//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

import java.io.ByteArrayOutputStream;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.codec.binary.Hex;

public class Utility {
    public Utility() {
    }

    public static String andHexString(String input, String mask) {
        return andHexStringOffset(input, mask, 0);
    }

    public static String andHexStringOffset(String input, String mask, int offset) {
        try {
            String moddedInput = input;
            if (input.length() % 2 == 1) {
                moddedInput = "0" + input;
            }

            byte[] data = Hex.decodeHex(moddedInput.toCharArray());
            String moddedMask = mask;
            if (mask.length() % 2 == 1) {
                moddedMask = mask + "F";
            }

            byte[] maskData = Hex.decodeHex(moddedMask.toCharArray());
            int os = offset / 2;
            int endPoint = os + maskData.length;

            int len;
            for(len = os; len < data.length && len < endPoint; ++len) {
                byte a = data[len];
                byte b = maskData[len - os];
                data[len] = (byte)(a & b);
            }

            len = input.length();
            String encodedData = hex(data).toUpperCase();
            return encodedData.substring(encodedData.length() - len);
        } catch (Exception var12) {
            throw new RuntimeException(var12);
        }
    }

    public static String desEncrypt(String key, String data) {
        int len = data.length();

        try {
            byte[] desKey = Hex.decodeHex(key.toCharArray());
            byte[] iv = new byte[8];
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            DESKeySpec keySpec = new DESKeySpec(desKey);
            SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            cipher.init(1, secretKey, paramSpec);
            byte[] encryptedData = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
            return hex(encryptedData).toUpperCase();
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        }
    }

    public static String orHexStringOffset(String input, String mask, int offset) {
        try {
            if (mask.length() % 2 == 1) {
                mask = "0" + mask;
            }

            byte[] data = Hex.decodeHex(input.toCharArray());
            byte[] maskData = Hex.decodeHex(mask.toCharArray());
            int os = offset / 2;
            int endPoint = os + maskData.length;

            for(int i = os; i < data.length && i < endPoint; ++i) {
                byte a = data[i];
                byte b = maskData[i - os];
                data[i] = (byte)(a | b);
            }

            return hex(data).toUpperCase();
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        }
    }

    public static String padLeft(String data, int length, char padChar) {
        int remaining = length - data.length();
        String newData = data;

        for(int i = 0; i < remaining; ++i) {
            newData = padChar + newData;
        }

        return newData;
    }

    public static String shiftRightHexString(String str) {
        long r = Long.parseLong(str, 16);
        r >>= 1;
        String shifted = Long.toString(r, 16);
        return padLeft(shifted, str.length(), '0');
    }

    public static String tripleDesEncrypt(String key, String data) {
        int len = data.length();

        try {
            byte[] keyBytes = Hex.decodeHex(key.toCharArray());
            byte[] desKey = new byte[24];
            System.arraycopy(keyBytes, 0, desKey, 0, 16);
            System.arraycopy(keyBytes, 0, desKey, 16, 8);
            DESedeKeySpec keySpec = new DESedeKeySpec(desKey);
            SecretKey secretKey = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
            Cipher ecipher = Cipher.getInstance("DESede/ECB/NoPadding");
            ecipher.init(1, secretKey);
            byte[] encryptedData = ecipher.doFinal(Hex.decodeHex(data.toCharArray()));
            return hex(encryptedData).toUpperCase();
        } catch (Exception var9) {
            throw new RuntimeException(var9);
        }
    }

    public static String xorHexString(String input, String mask) {
        try {
            byte[] data = Hex.decodeHex(input.toCharArray());
            byte[] maskData = Hex.decodeHex(mask.toCharArray());

            for(int i = 0; i < data.length; ++i) {
                data[i] ^= maskData[i];
            }

            return hex(data).toUpperCase();
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public static String incrementKsn(String ksn) {
        String tmpKsn = ksn.substring(0, 14);
        byte[] newKsn = hexStringToByteArray(ksn.substring(14));

        for(int loop = newKsn.length; loop > 0; --loop) {
            if (newKsn[loop - 1] != 255) {
                ++newKsn[loop - 1];
                break;
            }
        }

        return tmpKsn.concat(hex(newKsn));
    }

    public static String hex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        byte[] var2 = data;
        int var3 = data.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            sb.append(Character.forDigit((b & 240) >> 4, 16));
            sb.append(Character.forDigit(b & 15, 16));
        }

        return sb.toString();
    }

    public static String processPan(String pan) {
        String acqCode = pan.substring(0, 6);
        String endCode = pan.substring(pan.length() - 4);
        int elen = pan.length() - acqCode.length() - endCode.length();
        String etext = padLeft("*", elen, '*');
        String newPan = acqCode + etext + endCode;
        return newPan;
    }

    public static String getPan(String track2Data) {
        return track2Data.substring(0, track2Data.indexOf("D"));
    }

    public static String toHexString(byte[] b) {
        String result = "";

        for(int i = 0; i < b.length; ++i) {
            result = result + Integer.toString((b[i] & 255) + 256, 16).substring(1);
        }

        return result;
    }

    public static byte[] hexToByteArray(String s) {
        if (s == null) {
            s = "";
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        for(int i = 0; i < s.length() - 1; i += 2) {
            String data = s.substring(i, i + 2);
            bout.write(Integer.parseInt(data, 16));
        }

        return bout.toByteArray();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for(int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }

    public static String byte2HexStr(byte[] var0, int offset, int length) {
        if (var0 == null) {
            return "";
        } else {
            String var1 = "";
            StringBuilder var2 = new StringBuilder();

            for(int var3 = offset; var3 < offset + length; ++var3) {
                var1 = Integer.toHexString(var0[var3] & 255);
                var2.append(var1.length() == 1 ? "0" + var1 : var1);
            }

            return var2.toString().toUpperCase().trim();
        }
    }

    public static String bcd2Str(byte[] bcd) {
        if (bcd != null && bcd.length > 0) {
            char[] ascii = "0123456789abcdef".toCharArray();
            byte[] temp = new byte[bcd.length * 2];

            try {
                for(int i = 0; i < bcd.length; ++i) {
                    temp[i * 2] = (byte)(bcd[i] >> 4 & 15);
                    temp[i * 2 + 1] = (byte)(bcd[i] & 15);
                }

                StringBuffer res = new StringBuffer();

                for(int i = 0; i < temp.length; ++i) {
                    res.append(ascii[temp[i]]);
                }

                return res.toString().toUpperCase();
            } catch (Exception var5) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String ConcatLenght2Str(String tag, String value) {
        int lenght = value.length() / 2;
        String hexlenght;
        if (lenght <= 9) {
            hexlenght = "0" + lenght;
        } else {
            hexlenght = Integer.toHexString(lenght);
        }

        String tlv = tag + hexlenght + value;
        return tlv;
    }

    public static String byte2HexStr(byte[] var0) {
        if (var0 == null) {
            return "";
        } else {
            String var1 = "";
            StringBuilder var2 = new StringBuilder();

            for(int var3 = 0; var3 < var0.length; ++var3) {
                var1 = Integer.toHexString(var0[var3] & 255);
                var2.append(var1.length() == 1 ? "0" + var1 : var1);
            }

            return var2.toString().toUpperCase().trim();
        }
    }

    public static byte[] hexStr2Byte(String hexString) {
        if (hexString != null && hexString.length() != 0) {
            String hexStrTrimed = hexString.replace(" ", "");
            String hexStr = hexStrTrimed;
            int len = hexStrTrimed.length();
            if (len % 2 == 1) {
                hexStr = hexStrTrimed + "0";
                ++len;
            }

            byte[] result = new byte[len / 2];

            for(int i = 0; i < hexStr.length(); ++i) {
                char highChar = hexStr.charAt(i);
                char lowChar = hexStr.charAt(i + 1);
                int high = CHAR2INT(highChar);
                int low = CHAR2INT(lowChar);
                result[i / 2] = (byte)(high * 16 + low);
                ++i;
            }

            return result;
        } else {
            return new byte[]{0};
        }
    }

    public static byte[] hexStr2Byte(String hexString, int beginIndex, int length) {
        if (hexString != null && hexString.length() != 0) {
            if (length > hexString.length()) {
                length = hexString.length();
            }

            String hexStr = hexString;
            int len = length;
            if (length % 2 == 1) {
                hexStr = hexString + "0";
                len = length + 1;
            }

            byte[] result = new byte[len / 2];

            for(int i = beginIndex; i < len; ++i) {
                String s = hexStr.substring(i, i + 2);
                int v = Integer.parseInt(s, 16);
                result[i / 2] = (byte)v;
                ++i;
            }

            return result;
        } else {
            return new byte[]{0};
        }
    }

    public static byte HEX2DEC(int hex) {
        return (byte)(hex / 10 * 16 + hex % 10);
    }

    public static int DEC2INT(byte dec) {
        int high = (127 & dec) >> 4;
        if (0 != (128 & dec)) {
            high += 8;
        }

        return high * 10 + (dec & 15);
    }

    public static int CHAR2INT(char c) {
        if ((c < '0' || c > '9') && c != '=') {
            if (c >= 'a' && c <= 'f') {
                return c - 97 + 10;
            } else {
                return c >= 'A' && c <= 'F' ? c - 65 + 10 : 0;
            }
        } else {
            return c - 48;
        }
    }

    public static String getResponseCodeDefinition(String code) {
        switch (code) {
            case "00":
                return "Approved";
            case "01":
                return "Refer to card issuer";
            case "02":
                return "Refer to card issuer, special condition";
            case "03":
                return "Invalid merchant";
            case "04":
                return "Pick-up card";
            case "05":
                return "Do not honor";
            case "06":
                return "Host Error";
            case "07":
                return "Pick-up card, special condition";
            case "08":
                return "Honor with identification";
            case "09":
                return "Request in progress";
            case "10":
                return "Approved,partial";
            case "11":
                return "Approved,VIP";
            case "12":
                return "Invalid transaction";
            case "13":
                return "Invalid amount";
            case "14":
                return "Invalid card number";
            case "15":
                return "No such issuer";
            case "16":
                return "Approved,update track 3";
            case "17":
                return "Customer cancellation";
            case "18":
                return "Customer dispute";
            case "19":
                return "Re-enter transaction";
            case "20":
                return "Invalid response";
            case "21":
                return "No action taken";
            case "22":
                return "Suspected malfunction";
            case "23":
                return "Unacceptable transaction fee";
            case "24":
                return "File update not supported";
            case "25":
                return "Unable to locate record";
            case "26":
                return "Duplicate record";
            case "27":
                return "File update edit error";
            case "28":
                return "File update file locked";
            case "29":
                return "File update failed";
            case "30":
                return "Format error";
            case "31":
                return "Bank not supported";
            case "32":
                return "Completed, partially";
            case "33":
                return "Expired card, pick-up";
            case "34":
                return "Suspected fraud, pick-up";
            case "35":
                return "Contact acquirer, pick-up";
            case "36":
                return "Restricted card, pick-up";
            case "37":
                return "Call acquirer security, pick-up";
            case "38":
                return "PIN tries exceeded, pick-up";
            case "39":
                return "No credit account";
            case "40":
                return "Function not supported";
            case "41":
                return "Lost card";
            case "42":
                return "No universal account";
            case "43":
                return "Stolen card";
            case "44":
                return "No investment account";
            case "51":
                return "Not sufficent funds";
            case "52":
                return "No check account";
            case "53":
                return "No savings account";
            case "54":
                return "Expired card";
            case "55":
                return "Incorrect PIN";
            case "56":
                return "No card record";
            case "57":
                return "Transaction not permitted to cardholder";
            case "58":
                return "Transaction not permitted on terminal";
            case "59":
                return "Suspected fraud";
            case "60":
                return "Contact acquirer";
            case "61":
                return "Exceeds withdrawal limit";
            case "62":
                return "Restricted card";
            case "63":
                return "Security violation";
            case "64":
                return "Original amount incorrect";
            case "65":
                return "Exceeds withdrawal frequency";
            case "66":
                return "Call acquirer security";
            case "67":
                return "Hard capture";
            case "68":
                return "Response received too late";
            case "75":
                return "PIN tries exceeded";
            case "77":
                return "Intervene, bank approval required";
            case "78":
                return "Intervene, bank approval required for partial amount";
            case "90":
                return "Cut-off in progress";
            case "91":
                return "Issuer or switch inoperative";
            case "92":
                return "Routing error";
            case "93":
                return "Violation of law";
            case "94":
                return "Duplicate transaction";
            case "95":
                return "Reconcile error";
            case "96":
                return "System malfunction";
            case "98":
                return " Exceeds cash limit";
            case "99":
                return "Issuer or switch inoperative";
            default:
                return "Issuer or switch inoperative";
        }
    }
}
