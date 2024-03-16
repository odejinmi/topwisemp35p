//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

public class TripleDES {
    public TripleDES() {
    }

    public static SecretKey readKey(byte[] rawkey) {
        try {
            DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
            SecretKey key = keyfactory.generateSecret(keyspec);
            key = keyfactory.translateKey(key);
            return key;
        } catch (InvalidKeySpecException var4) {
            return null;
        } catch (NoSuchAlgorithmException var5) {
            return null;
        } catch (InvalidKeyException var6) {
            return null;
        }
    }

    public static SecretKey readSingleKey(byte[] rawkey) throws IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchProviderException {
        DESKeySpec keyspec = new DESKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyfactory.generateSecret(keyspec);
        return key;
    }

    public static int icclenght(String s) {
        int iLength = s.length();
        int iBuff = iLength / 2;
        return iBuff;
    }

    public static byte[] hexStringToBytes(String s) {
        int iLength = s.length();
        int iBuff = iLength / 2;
        byte[] buff = new byte[iBuff];
        int j = 0;

        for(int i = 0; i < iLength; i += 2) {
            String s1 = s.substring(i, i + 2);
            buff[j++] = (byte)Integer.parseInt(s1, 16);
        }

        return buff;
    }

    public static String threeDesEncrypt(String keyComponent1, String keyComponent2, String encryptedToken) {
        byte[] keyB1 = hexToByte(keyComponent1 + keyComponent1.substring(0, 16));
        byte[] keyB2 = hexToByte(keyComponent2 + keyComponent2.substring(0, 16));

        for(int i = 0; i < keyB2.length; ++i) {
            keyB1[i] ^= keyB2[i];
        }

        SecretKey key = readKey(keyB1);
        return Encrypt(key, encryptedToken);
    }

    public static String threeDesDecrypt(String keyComponent1, String keyComponent2, String encryptedToken) {
        byte[] keyB1 = hexToByte(keyComponent1 + keyComponent1.substring(0, 16));
        byte[] keyB2 = hexToByte(keyComponent2 + keyComponent2.substring(0, 16));

        for(int i = 0; i < keyB2.length; ++i) {
            keyB1[i] ^= keyB2[i];
        }

        SecretKey key = readKey(keyB1);
        return Decrypt(key, encryptedToken);
    }

    public static String threeDesDecrypt(String encryptedToken, String key) {
        byte[] mkB = hexToByte(key + key.substring(0, 16));
        SecretKey keyse = readKey(mkB);
        return Decrypt(keyse, encryptedToken);
    }

    public static String encryptKey(String cipherText, String encryptingKey) {
        byte[] mkB = hexToByte(encryptingKey + encryptingKey.substring(0, 16));
        SecretKey keyse = readKey(mkB);
        return Encrypt(keyse, cipherText);
    }

    public static String Pinkey(String key1, String key2) {
        byte[] mkB = hexToByte(key2 + key2.substring(0, 16));
        SecretKey keyse = readKey(mkB);
        String pinkeyresult = Decrypt(keyse, key1);
        return pinkeyresult;
    }

    public static String generateHash256Value(String msg, String key) throws UnsupportedEncodingException {
        MessageDigest m = null;
        String hashText = null;
        byte[] actualKeyBytes = hexStringToBytes(key);

        try {
            m = MessageDigest.getInstance("SHA-256");
            m.update(actualKeyBytes, 0, actualKeyBytes.length);
            m.update(msg.getBytes(StandardCharsets.UTF_8), 0, msg.length());
            hashText = (new BigInteger(1, m.digest())).toString(16);
        } catch (NoSuchAlgorithmException var8) {
            var8.printStackTrace();
        }

        if (hashText.length() < 64) {
            int numberOfZeroes = 64 - hashText.length();
            String zeroes = "";

            for(int i = 0; i < numberOfZeroes; ++i) {
                zeroes = zeroes + "0";
            }

            hashText = zeroes + hashText;
        }

        return hashText;
    }

    public static String EncryptDES(Key key, String clearComp) throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, key);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] clearText = hexToByte(clearComp);
            CipherOutputStream out = new CipherOutputStream(bytes, cipher);
            out.write(clearText);
            out.flush();
            out.close();
            byte[] ciphertext = bytes.toByteArray();
            bytes.flush();
            bytes.close();
            String encrypted = ToHexString(ciphertext);
            Arrays.fill(clearText, (byte)0);
            Arrays.fill(ciphertext, (byte)0);
            return encrypted;
        } catch (InvalidKeyException var8) {
            return null;
        }
    }

    public static String Encrypt(Key key, String clearComp) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(1, key);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] clearText = hexToByte(clearComp);
            CipherOutputStream out = new CipherOutputStream(bytes, cipher);
            out.write(clearText);
            out.flush();
            out.close();
            byte[] ciphertext = bytes.toByteArray();
            bytes.flush();
            bytes.close();
            String encrypted = ToHexString(ciphertext);
            Arrays.fill(clearText, (byte)0);
            Arrays.fill(ciphertext, (byte)0);
            return encrypted;
        } catch (IOException var8) {
            return null;
        } catch (NoSuchPaddingException var9) {
            return null;
        } catch (NoSuchAlgorithmException var10) {
            return null;
        } catch (InvalidKeyException var11) {
            return null;
        }
    }

    public static String Decrypt(Key key, String cipherComp) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(2, key);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] ciphertext = hexToByte(cipherComp);
            CipherOutputStream out = new CipherOutputStream(bytes, cipher);
            out.write(ciphertext);
            out.flush();
            out.close();
            byte[] deciphertext = bytes.toByteArray();
            bytes.flush();
            bytes.close();
            String decrypted = ToHexString(deciphertext);
            Arrays.fill(ciphertext, (byte)0);
            Arrays.fill(deciphertext, (byte)0);
            return decrypted;
        } catch (IOException var8) {
            return null;
        } catch (NoSuchPaddingException var9) {
            return null;
        } catch (NoSuchAlgorithmException var10) {
            return null;
        } catch (InvalidKeyException var11) {
            return null;
        }
    }

    public static String DecryptDES(Key key, String cipherComp) throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, key);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            byte[] cipherText = hexToByte(cipherComp);
            CipherOutputStream out = new CipherOutputStream(bytes, cipher);
            out.write(cipherText);
            out.flush();
            out.close();
            byte[] deciphertext = bytes.toByteArray();
            bytes.flush();
            bytes.close();
            String decrypted = ToHexString(deciphertext);
            Arrays.fill(cipherText, (byte)0);
            Arrays.fill(deciphertext, (byte)0);
            return decrypted;
        } catch (InvalidKeyException var8) {
            return null;
        }
    }

    public static String ToHexString(byte[] toAsciiData) {
        String hexString = "";
        byte[] var2 = toAsciiData;
        int var3 = toAsciiData.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            hexString = hexString + String.format("%02X", b);
        }

        return hexString;
    }

    public static byte[] hexToByte(String hexString) {
        String str = "0123456789ABCDEF";
        byte[] bytes = new byte[hexString.length() / 2];
        int i = 0;

        for(int j = 0; i < hexString.length(); ++i) {
            byte firstQuad = (byte)(str.indexOf(hexString.charAt(i)) << 4);
            ++i;
            byte secondQuad = (byte)str.indexOf(hexString.charAt(i));
            bytes[j++] = (byte)(firstQuad | secondQuad);
        }

        return bytes;
    }

    public static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 8);

        for(int i = 0; i < 8 * bytes.length; ++i) {
            sb.append((char)((bytes[i / 8] << i % 8 & 128) == 0 ? '0' : '1'));
        }

        return sb.toString();
    }

    public static String hexToBinary(String hex) {
        String binAddr = Integer.toBinaryString(Integer.parseInt(hex, 16));
        String.format("%032", new BigInteger(binAddr));
        return binAddr;
    }

    public static byte[] intToBytes(int x) {
        byte[] bytes = new byte[2];

        for(int i = 0; x != 0; x >>>= 8) {
            bytes[i] = (byte)(x & 255);
            ++i;
        }

        return bytes;
    }

    public static String hexToBin(String hex) {
        String bin = "";
        String binFragment = "";
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for(int i = 0; i < hex.length(); ++i) {
            int iHex = Integer.parseInt("" + hex.charAt(i), 16);

            for(binFragment = Integer.toBinaryString(iHex); binFragment.length() < 4; binFragment = "0" + binFragment) {
            }

            bin = bin + binFragment;
        }

        return bin;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        byte[] var3 = bytes;
        int var4 = bytes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            formatter.format("%02x", b);
        }

        return sb.toString();
    }

    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();

        for(int i = 0; i < chars.length; ++i) {
            hex.append(Integer.toHexString(chars[i]));
        }

        return hex.toString();
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        }

        return sb.toString();
    }

    public static final byte[] append(byte[]... arrays) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (arrays != null) {
            byte[][] var2 = arrays;
            int var3 = arrays.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte[] array = var2[var4];
                if (array != null) {
                    out.write(array, 0, array.length);
                }
            }
        }

        return out.toByteArray();
    }

    private static String hexToASCII(String hexValue) {
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < hexValue.length(); i += 2) {
            String str = hexValue.substring(i, i + 2);
            output.append((char)Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    public static void main(String[] ad) throws IOException {
        String key1 = "10101010101010101010101010101010";
        String key2 = "01010101010101010101010101010101";
        String key3 = "FDCE7989D5C2940DD63EBA867307A401";
        byte[] keyB1 = hexToByte(key1 + key1.substring(0, 16));
        byte[] keyB2 = hexToByte(key2 + key2.substring(0, 16));
        byte[] keyB3 = hexToByte(key3 + key3.substring(0, 16));

        for(int i = 0; i < keyB2.length; ++i) {
            keyB1[i] ^= keyB2[i];
        }

        SecretKey key = readKey(keyB1);
        String MK = threeDesDecrypt("5D25072F04832A2329D93E4F91BA23A2", "86CBCDE3B0A22354853E04521686863D", "8C198ADC0ACDD8539F500AE253A018DD");
        String SK = threeDesDecrypt("1BEA821376EE9F7F052ADB648C37E6A9", "6437896204BA4F3BFD0B0E7AAD92A213");
        String PK = threeDesDecrypt("24C28760D48490208CF73D9DBD6EE186", "DAE94CF2DFE058D54A075E584CE99B0B");
        String hsd = generateHash256Value("0200723846D029A08205000000001000002100202050610516384269045999051123000324072607004606072607032400000000100012D12345678111129345061051638426904D1610221010374591412000000000034221  EDC DEMO APPLICATION   SPECTRA TECHNOL566950580800480009F1A0203569F2608F96210F14EE068DA9F2701805F2A0207849F02060000000100009F360200259F37041F7754609F3501219F34034203009F3303E0F8009F03060000000000009A031407039F0902008C9C01009F4104000000489F100706010A03A0A8009F1E08313233343536373882025C008407A00000000310105F3401010155101015113441010A4ED5798BD1DBAFB720A0050FC591B2C91BA9D6AAEB457EE224221E4FC0F3F420390004014010092335424156481EE565334582325312E11CFEDED7B33C8850CC96074F5574AEC2123F7684A", "799B0897E5A1675BC2082F989E804AD9");
        String param = "008D303830303232333830303030303038303030303539433030303030333234303732363037303034323433303732363037303332343230333930303034303134303130303932333335343234313536666631636237613230663032613064666538656235376361366462653062616538343566623161633739656533613237343431616237363766303561323531";
        String test2 = "080022380000008000009A00000912140912000001140912091220390004";
        String test1 = "0200723846D029A08205000000001000002100202050610516384269045999051123000324072607004606072607032400000000100012D12345678111129345061051638426904D1610221010374591412000000000034221  EDC DEMO APPLICATION   SPECTRA TECHNOL566950580800480009F1A0203569F2608F96210F14EE068DA9F2701805F2A0207849F02060000000100009F360200259F37041F7754609F3501219F34034203009F3303E0F8009F03060000000000009A031407039F0902008C9C01009F4104000000489F100706010A03A0A8009F1E08313233343536373882025C008407A00000000310105F3401010155101015113441010A4ED5798BD1DBAFB720A0050FC591B2C91BA9D6AAEB457EE224221E4FC0F3F420390004014010092335424156481EE565334582325312E11CFEDED7B33C8850CC96074F5574AEC2123F7684A" + hsd.toUpperCase();
        int x = test1.length();
        String binlng = Integer.toBinaryString(x);
        String hexlng = Integer.toHexString(Integer.parseInt(binlng, 2));
        String test = "130482308100008000000000000008000000140930180200000114093018025909303011234567813POS PARAMETER";
        StringBuffer hex1 = new StringBuffer();

        for(int i = 0; i < test2.length(); ++i) {
            if (test2.charAt(i) <= '\t') {
                hex1.append('0');
            }

            hex1.append(Integer.toHexString(test2.charAt(i)));
        }

        String contenttohex = "";

        for(int i = 0; i < test1.length(); ++i) {
            if (test1.charAt(i) <= '\t') {
                contenttohex = contenttohex + '0';
            }

            contenttohex = contenttohex + Integer.toHexString(test1.charAt(i));
        }

        Calendar time = null;
        new GregorianCalendar();
        hexlng = String.format("%4s", hexlng).replace(' ', '0');
        String msg = "ITEX~102";
        new BufferedReader(new InputStreamReader(System.in));
        String hex = hexlng.toUpperCase() + contenttohex;
        int width = hex.length();
        int g = 18878464;
        byte[] databyte = hexToByte(hex);
        byte[] tbit0 = new byte[1];
        byte[] tbit2 = new byte[1];
        byte[] tbit4 = new byte[1];
        byte[] tbit6 = new byte[1];
        byte[] tbit8 = new byte[1];
        byte[] tbit10 = new byte[1];
        byte[] tbit12 = new byte[1];
        byte[] tbit14 = new byte[1];
        byte[] tbit16 = new byte[1];
        byte[] tbit18 = new byte[1];
        byte[] tbit20 = new byte[1];
        byte[] tbit22 = new byte[1];
        byte[] tbit24 = new byte[1];
        byte[] tbit26 = new byte[1];
        byte[] tbit28 = new byte[1];
        byte[] tbit30 = new byte[1];
        String bitall = "82308100008000000000000008000000";

        for(int i = 0; i < bitall.length(); ++i) {
            if (i == 0) {
                tbit0 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 2) {
                tbit2 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 4) {
                tbit4 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 6) {
                tbit6 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 8) {
                tbit8 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 10) {
                tbit10 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 12) {
                tbit12 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 14) {
                tbit14 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 16) {
                tbit16 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 18) {
                tbit18 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 20) {
                tbit20 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 22) {
                tbit22 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 24) {
                tbit24 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 26) {
                tbit26 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 28) {
                tbit28 = hexToByte(bitall.substring(i, i + 2));
            }

            if (i == 30) {
                tbit30 = hexToByte(bitall.substring(i, i + 2));
            }

            ++i;
        }

        byte[] finalarray = new byte[16];
        System.arraycopy(tbit0, 0, finalarray, 0, tbit0.length);
        System.arraycopy(tbit2, 0, finalarray, 1, tbit2.length);
        System.arraycopy(tbit4, 0, finalarray, 2, tbit4.length);
        System.arraycopy(tbit6, 0, finalarray, 3, tbit6.length);
        System.arraycopy(tbit8, 0, finalarray, 4, tbit8.length);
        System.arraycopy(tbit10, 0, finalarray, 5, tbit10.length);
        System.arraycopy(tbit12, 0, finalarray, 6, tbit12.length);
        System.arraycopy(tbit14, 0, finalarray, 7, tbit14.length);
        System.arraycopy(tbit16, 0, finalarray, 8, tbit16.length);
        System.arraycopy(tbit18, 0, finalarray, 9, tbit18.length);
        System.arraycopy(tbit20, 0, finalarray, 10, tbit20.length);
        System.arraycopy(tbit22, 0, finalarray, 11, tbit22.length);
        System.arraycopy(tbit24, 0, finalarray, 12, tbit24.length);
        System.arraycopy(tbit26, 0, finalarray, 13, tbit26.length);
        System.arraycopy(tbit28, 0, finalarray, 14, tbit28.length);
        System.arraycopy(tbit30, 0, finalarray, 15, tbit30.length);
        String s = new String(finalarray, 0, 16);
        StringBuilder sb = new StringBuilder(new String(finalarray, 0, finalarray.length - 1));
        String alltostring = "ISO00942100010001304" + s + "141002154900000114100215492110023011234567813POS PARAMETER";
        new StringBuilder("ISO00942100010001304" + sb + "141002154900000114100215492110023011234567813POS PARAMETER");

        try {
            URL url = new URL("http://192.168.0.245/tams/tams/devinterface/newkeys.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "lipman");
            httpURLConnection.setRequestProperty("Terminalt", "203300CM");
            httpURLConnection.setRequestProperty("EOD", "0");
            httpURLConnection.setRequestProperty("Sign", "lipman");
        } catch (MalformedURLException var81) {
        }

        String receive = "0110F23C46D12BE08200000000000000002116539983163961989531100000000002000002251610090061281610090225160159990510000012D0000000106111129345399831639619895D160122100092353500000000008809622120390004039000000000040SUCCESS KITCHEN LTD                     5661889F260840BEB3C7E400C4B39F2701809F100706010A03A4A8009F3704C00A04489F3602004E950500000080009A031502109C01009F02060000000000015F2A02056682025C009F1A0205669F3303E0F8E89F34034102029F1E04280081800155101015113441014181d5f1368e5d52ef49610e0d4bff4f42b3988385adb1987b3faa7a3b703090";
        int bitmaptype = 1;
        String bitm = hexToBin("F23C44D12BE082000000000000000021");
        String field = "";
        int num = 1;

        for(int gd = 0; gd < bitm.length(); ++gd) {
            if (gd == 0 && bitm.charAt(gd) == 1) {
                bitmaptype = 1;
            }

            if (bitm.charAt(gd) == '1') {
                field = field + num + ",";
            }

            ++num;
        }

        bitmaptype = 32;
        String[] ary = field.split(",");

        String stdIn;
        for(int o = 0; o < ary.length; ++o) {
            int fvalues = Integer.parseInt(ary[o]);
            int var10000;
            int b;
            int e;
            int i;
            String lk1;
            int e1;
            int i1;
            String lk2;
            int e2;
            int e3;
            String lk3;
            int e4;
            int e5;
            String lk4;
            int e6;
            switch (fvalues) {
                case 2:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn);
                    break;
                case 3:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    var10000 = b + e;
                    break;
                case 4:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6;
                    break;
                case 7:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12;
                    break;
                case 11:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10;
                    break;
                case 12:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6;
                    break;
                case 13:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6;
                    break;
                case 14:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4;
                    break;
                case 18:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4;
                    break;
                case 22:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4;
                    break;
                case 23:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3;
                    break;
                case 25:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3;
                    break;
                case 26:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2;
                    break;
                case 28:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2;
                    break;
                case 32:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    break;
                case 35:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    break;
                case 37:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    var10000 = i1 + e2;
                    break;
                case 39:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12;
                    break;
                case 40:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2;
                    break;
                case 41:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2 + 3;
                    break;
                case 42:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2 + 3 + 8;
                    break;
                case 43:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2 + 3 + 8 + 15;
                    break;
                case 49:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2 + 3 + 8 + 15 + 40;
                    break;
                case 55:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2 + 3 + 8 + 15 + 40 + 3;
                    lk3 = receive.substring(e3, e3 + 3);
                    e4 = Integer.parseInt(lk3) + 3;
                    break;
                case 62:
                    b = receive.length() - 64;
                    e = receive.length();
                    e = receive.length() - 104;
                    i = e + 40;
                    int b3 = receive.length() - 113;
                    e1 = b3 + 4;
                    i1 = receive.length() - 127;
                    e4 = i1 + 3;
                    e2 = receive.length() - 135;
                    e3 = e2 + 3;
                    int b6 = receive.length() - 161;
                    e4 = b6 + 15;
                    e5 = receive.length() - 188;
                    int var97 = e5 + 16;
                    break;
                case 123:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2 + 3 + 8 + 15 + 40 + 3;
                    lk3 = receive.substring(e3, e3 + 3);
                    e4 = Integer.parseInt(lk3) + 3;
                    e5 = e3 + e4;
                    lk4 = receive.substring(e5, e5 + 3);
                    e6 = Integer.parseInt(lk4) + 3;
                    break;
                case 128:
                    b = bitmaptype + 4;
                    stdIn = receive.substring(b, b + 2);
                    e = Integer.parseInt(stdIn) + 2;
                    i = b + e + 6 + 12 + 10 + 6 + 6 + 4 + 4 + 4 + 3 + 3 + 2 + 2 + 9;
                    lk1 = receive.substring(i, i + 2);
                    e1 = Integer.parseInt(lk1) + 2;
                    i1 = i + e1;
                    lk2 = receive.substring(i1, i1 + 2);
                    e2 = Integer.parseInt(lk2) + 2;
                    e3 = i1 + e2 + 12 + 2 + 3 + 8 + 15 + 40 + 3;
                    lk3 = receive.substring(e3, e3 + 3);
                    e4 = Integer.parseInt(lk3) + 3;
                    e5 = e3 + e4;
                    lk4 = receive.substring(e5, e5 + 3);
                    e6 = Integer.parseInt(lk4) + 3;
                    var10000 = e5 + e6;
            }
        }

        Socket echoSocket = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        stdIn = "08008238000000000000040000000000000009261256591256590926000237301";

        try {
            echoSocket = new Socket("196.6.103.10", 55531);
            os = new DataOutputStream(echoSocket.getOutputStream());
            is = new DataInputStream(echoSocket.getInputStream());
        } catch (UnknownHostException var79) {
            System.err.println("Don't know about host: taranis");
        } catch (IOException var80) {
            System.err.println("Couldn't get I/O for the connection to: taranis");
        }

        if (echoSocket != null && os != null && is != null) {
            try {
                os.writeBytes(stdIn);
                os.close();
                is.close();
                echoSocket.close();
            } catch (IOException var78) {
                System.err.println("I/O failed on the connection to: taranis" + var78);
            }
        }

    }
}
