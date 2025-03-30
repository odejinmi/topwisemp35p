package com.lonytech.topwisesdk.emvreader

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException

object ApiClient {
    private const val BASE_URL =  "https://abs.paylony.com/api/v1/"
    private const val API_KEY = "pos_hash_wv9b8dqovdpmz6r9v47gjtjy"

    private val client = OkHttpClient()

    val gson: Gson
//    val retrofit: Retrofit

    init {


        gson = GsonBuilder().setLenient().create()

//        retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
    }

    suspend fun post(endpoint: String, data: Map<String, String?>, context: Context, token: String): JSONObject {
        return withContext(Dispatchers.IO) {
            if (!hasInternetConnection(context)) {
                return@withContext JSONObject().apply {
                    put("success", false)
                    put("message", "No Internet Connection. Try another Network")
                    put("status", "false")
                }
            }

            val url = "$BASE_URL$endpoint"
            val ivString = generateRandomString(16)
            val secretKey = API_KEY.take(32).toByteArray()
            val iv = IvParameterSpec(ivString.toByteArray())

            val encryptedData = encryptData(JSONObject(data).toString(), secretKey, iv)

            val headers = mapOf(
                "Content-Type" to "application/json",
                "serialNumber" to getDeviceSerialNumber,
                "deviceName" to "MP35P",
                "Terminal-Auth" to hashedString(data["reference"]?.toString() ?: "", data["terminalId"]?.toString() ?: "" ),
                "Authorization" to "Bearer $token",
                "timestamp" to ivString
            )

            val payload = if (endpoint in listOf(
                    "payment/cardLess/debit",
                    "payment/card/debit",
                    "card-balance"
                )) {
                mapOf("data" to encryptedData)
            } else {
                data
            }

            Log.d("API", "URL: $url")
            Log.d("API", "Headers: $headers")
            Log.d("API", "Unencrypted Payload: $data")
            Log.d("API", "Encrypted Payload: $payload")

            val requestBody = Gson().toJson(payload).toRequestBody("application/json".toMediaType())
            val request = Request.Builder().url(url).post(requestBody)
            headers.forEach { (key, value) -> request.addHeader(key, value) }

            try {
                val response = client.newCall(request.build()).execute()
                val responseBody = response.body?.string()
                Log.d("API", "Response: $responseBody")

                return@withContext if (response.isSuccessful) {
                    JSONObject(responseBody ?: "{}")
                } else if (response.code == 401) {
                    // Handle 401 - Unauthorized
                    return@withContext JSONObject().apply {
                        put("success", false)
                        put("message", "Try and login again")
                        put("status", "false")
                    }
                } else {
                    JSONObject().apply {
                        put("success", false)
                        put("message", "Unexpected error")
                        put("status", "false")
                    }
                }
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
                return@withContext JSONObject().apply {
                    put("success", false)
                    put("message", e.message)
                    put("status", "false")
                }
            }
        }
    }

    private fun hasInternetConnection(context: Context): Boolean {
        // Implement network check logic (ConnectivityManager)
        return true
    }

    val getDeviceSerialNumber: String
        get() = DeviceTopUsdkServiceManager.instance?.systemManager?.serialNo!!


    private fun hashedString(reference: String,terminalId: String): String {
        val join = "${terminalId}|${getDeviceSerialNumber}|MP35P|$reference"
        println("join: $join") // Equivalent to debugPrint

        val digest = MessageDigest.getInstance("SHA-512")
        val hashBytes = digest.digest(join.toByteArray(StandardCharsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun generateRandomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length).map { chars.random() }.joinToString("")
    }

    private fun encryptData(data: String, key: ByteArray, iv: IvParameterSpec): String {
        val secretKeySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)
        val encrypted = cipher.doFinal(data.toByteArray())
        return encrypted.joinToString("") { "%02x".format(it) }
    }
}
