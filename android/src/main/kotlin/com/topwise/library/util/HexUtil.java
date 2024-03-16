//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

public class HexUtil {
    public HexUtil() {
    }

    public static byte[] hexStringToByte(String data) {
        String hex = data.toUpperCase();
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    public static byte[] StringToByte(String str) {
        int len = str.length();
        byte[] result = new byte[len];
        char[] achar = str.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)achar[i];
        }

        return result;
    }

    public static String bcd2str(byte[] bcds) {
        if (bcds == null) {
            return "";
        } else {
            char[] ascii = "0123456789abcdef".toCharArray();
            byte[] temp = new byte[bcds.length * 2];

            for(int i = 0; i < bcds.length; ++i) {
                temp[i * 2] = (byte)(bcds[i] >> 4 & 15);
                temp[i * 2 + 1] = (byte)(bcds[i] & 15);
            }

            StringBuffer res = new StringBuffer();

            for(int i = 0; i < temp.length; ++i) {
                res.append(ascii[temp[i]]);
            }

            return res.toString().toUpperCase();
        }
    }

    private static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }

    public static byte[] int2bytes(int num) {
        byte[] b = new byte[4];
        int mask = 1;

        for(int i = 0; i < 4; ++i) {
            b[i] = (byte)(num >>> 24 - i * 8);
        }

        return b;
    }

    public static byte[] int2bytesNew(int num) {
        byte[] b = new byte[4];
        int mask = 1;

        for(int i = 0; i < 4; ++i) {
            b[i] = (byte)(num >>> i * 8);
        }

        return b;
    }

    public static int bytes2int(byte[] b) {
        int mask = 255;
        int temp = 0;
        int res = 0;

        for(int i = 0; i < 4; ++i) {
            res <<= 8;
            temp = b[i] & mask;
            res |= temp;
        }

        return res;
    }

    public static byte[] short2bytes(short num) {
        byte[] targets = new byte[2];

        for(int i = 0; i < 2; ++i) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte)(num >>> offset & 255);
        }

        return targets;
    }

    public static int bytes2short(byte[] b) {
        int mask = 255;
        int temp = 0;
        int res = 0;

        for(int i = 0; i < 2; ++i) {
            res <<= 8;
             temp = b[i] & mask;
            res |= temp;
        }

        return res;
    }

    public static String getBinaryStrFromByteArr(byte[] bArr) {
        String result = "";
        byte[] var2 = bArr;
        int var3 = bArr.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            result = result + getBinaryStrFromByte(b);
        }

        return result;
    }

    public static String getBinaryStrFromByte(byte b) {
        String result = "";
        byte a = b;

        for(int i = 0; i < 8; ++i) {
            byte c = a;
            a = (byte)(a >> 1);
            a = (byte)(a << 1);
            if (a == c) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }

            a = (byte)(a >> 1);
        }

        return result;
    }

    public static String getBinaryStrFromByte2(byte b) {
        String result = "";
        byte a = b;

        for(int i = 0; i < 8; ++i) {
            result = a % 2 + result;
            a = (byte)(a >> 1);
        }

        return result;
    }

    public static String getBinaryStrFromByte3(byte b) {
        String result = "";
        byte a = b;

        for(int i = 0; i < 8; ++i) {
            result = a % 2 + result;
            a = (byte)(a / 2);
        }

        return result;
    }

    public static byte[] toByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];

        for(int i = 0; i < 4 && i < iArrayLen; ++i) {
            bLocalArr[i] = (byte)(iSource >> 8 * i & 255);
        }

        return bLocalArr;
    }

    public static byte[] xor(byte[] op1, byte[] op2) {
        if (op1.length != op2.length) {
            throw new IllegalArgumentException("参数错误，长度不�?��");
        } else {
            byte[] result = new byte[op1.length];

            for(int i = 0; i < op1.length; ++i) {
                result[i] = (byte)(op1[i] ^ op2[i]);
            }

            return result;
        }
    }
}
