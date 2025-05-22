package com.lonytech.topwisesdk

import android.net.Uri
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HttpJsonParser {
    private var inputStream: InputStream? = null
    private var jsonObject: JSONObject? = null
    private var json = ""
    private  val BASE_URL =  "https://abs.paylony.com/api/v1/"
    private  val API_KEY = "pos_hash_wv9b8dqovdpmz6r9v47gjtjy"

    fun makeHttpRequest(endpoint: String, method: String, params: Map<String, String?>?, headers:  Map<String, String>, ivString: String): JSONObject? {
        try {
            val url = "${BASE_URL}$endpoint"

            val secretKey = API_KEY.take(32).toByteArray()
            val iv = IvParameterSpec(ivString.toByteArray())

            val encryptedData = encryptData(JSONObject(params!!).toString(), secretKey, iv)

            val payload = if (endpoint in listOf(
                    "payment/cardLess/debit",
                    "payment/card/debit",
                    "card-balance"
                )) {
                mapOf("data" to encryptedData)
            } else {
                params
            }
            val builder = Uri.Builder()
            payload.forEach { entry ->
                builder.appendQueryParameter(entry.key, entry.value)
            }
            val encodedParams = builder.build().encodedQuery ?: ""
            val urlConnection = getUnsafeHttpURLConnection("$url?$encodedParams")

            Log.d("API", "URL: $url")
            Log.d("API", "Headers: $headers")
            Log.d("API", "Unencrypted Payload: $params")
            Log.d("API", "Encrypted Payload: $payload")

            urlConnection.requestMethod = method.replace("&TOKEN", "")
            headers.forEach { entry ->
                urlConnection.setRequestProperty(entry.key, entry.value)
            }
//            urlConnection.setRequestProperty("version", "1.0")
//            urlConnection.setRequestProperty("agent_code", "123456")
//            urlConnection.setRequestProperty("devicename", "Android Device")
//            if (method.contains("TOKEN")) {
//                urlConnection.setRequestProperty("Authorization", "Bearer $token")
//            }

            if (method == "POST" || method == "PATCH") {
                urlConnection.doOutput = true
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                urlConnection.outputStream.write(encodedParams.toByteArray())
            }

            urlConnection.connect()
            val status = urlConnection.responseCode
            inputStream = if (status < 400) urlConnection.inputStream else urlConnection.errorStream

            val reader = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            inputStream?.close()
            json = sb.toString()
            jsonObject = JSONObject(json)
        } catch (e: Exception) {
            Log.e("HttpJsonParser", "Error: ${e.message}")
        }
        return jsonObject
    }

    private fun encryptData(data: String, key: ByteArray, iv: IvParameterSpec): String {
        val secretKeySpec = SecretKeySpec(key, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)
        val encrypted = cipher.doFinal(data.toByteArray())
        return encrypted.joinToString("") { "%02x".format(it) }
    }

    fun getUnsafeHttpURLConnection(url: String): HttpURLConnection {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        val urlObj = URL(url)
        val connection = urlObj.openConnection() as HttpsURLConnection
        connection.sslSocketFactory = sslContext.socketFactory
        connection.hostnameVerifier = HostnameVerifier { _, _ -> true }

        return connection
    }
}
