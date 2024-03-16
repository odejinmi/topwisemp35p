//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

import android.util.Log;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class TLVDecode {
    private static final String TAG = TLVDecode.class.getSimpleName();
    private static LinkedHashMap<byte[], byte[]> mList;

    public TLVDecode() {
    }

    public static LinkedHashMap<byte[], byte[]> getDecodeTLV(byte[] tlv) {
        Log.d(TAG, "getDecodeTLV tlv:  " + BCDASCII.bytesToHexString(tlv));
        mList = new LinkedHashMap();
        decodeTLV(tlv);
        Iterator var1 = mList.keySet().iterator();

        while(var1.hasNext()) {
            byte[] item = (byte[])var1.next();
            Log.i(TAG, "getDecodeTLV item: " + BCDASCII.bytesToHexString(item) + "  mList.get(item):" + BCDASCII.bytesToHexString((byte[])mList.get(item)));
        }

        return mList;
    }

    private static void decodeTLV(byte[] tlv) {
        if (tlv != null && tlv.length >= 1) {
            byte[] tag;
            byte[] value;
            byte[] nextTlv;
            int valueLen;
            int lenLen;
            if ((tlv[0] & 32) != 32) {
                if ((tlv[0] & 31) != 31) {
                    valueLen = (tlv[1] & 129) != 129 ? tlv[1] & 255 : tlv[2] & 255;
                    lenLen = (tlv[1] & 129) != 129 ? 1 : 2;
                    tag = new byte[1];
                    value = new byte[valueLen];
                    System.arraycopy(tlv, 0, tag, 0, 1);
                    System.arraycopy(tlv, 1 + lenLen, value, 0, valueLen);
                    mList.put(tag, value);
                    Log.i(TAG, "mList_1 tag: " + BCDASCII.bytesToHexString(tag) + "  tag: " + BCDASCII.bytesToHexString(value));
                    if (tlv.length > 1 + lenLen + value.length) {
                        nextTlv = new byte[tlv.length - 1 - lenLen - valueLen];
                        System.arraycopy(tlv, 1 + lenLen + valueLen, nextTlv, 0, tlv.length - 1 - lenLen - valueLen);
                        decodeTLV(nextTlv);
                    }
                } else {
                    valueLen = (tlv[2] & 129) != 129 ? tlv[2] & 255 : tlv[3] & 255;
                    lenLen = (tlv[2] & 129) != 129 ? 1 : 2;
                    tag = new byte[2];
                    value = new byte[valueLen];
                    System.arraycopy(tlv, 0, tag, 0, 2);
                    System.arraycopy(tlv, 2 + lenLen, value, 0, valueLen);
                    mList.put(tag, value);
                    Log.i(TAG, "mList_2 tag: " + BCDASCII.bytesToHexString(tag) + "  tag: " + BCDASCII.bytesToHexString(value));
                    if (tlv.length > 2 + lenLen + value.length) {
                        nextTlv = new byte[tlv.length - 2 - lenLen - valueLen];
                        System.arraycopy(tlv, 2 + lenLen + valueLen, nextTlv, 0, tlv.length - 2 - lenLen - valueLen);
                        decodeTLV(nextTlv);
                    }
                }
            } else if ((tlv[0] & 31) != 31) {
                valueLen = (tlv[2] & 129) != 129 ? tlv[1] & 255 : tlv[2] & 255;
                lenLen = (tlv[2] & 129) != 129 ? 1 : 2;
                tag = new byte[1];
                value = new byte[valueLen];
                System.arraycopy(tlv, 0, tag, 0, 1);
                System.arraycopy(tlv, 1 + lenLen, value, 0, valueLen);
                mList.put(tag, value);
                Log.i(TAG, "mList_3 tag: " + BCDASCII.bytesToHexString(tag) + "  tag: " + BCDASCII.bytesToHexString(value));
                decodeTLV(value);
                if (tlv.length > 1 + lenLen + valueLen) {
                    nextTlv = new byte[tlv.length - 1 - lenLen - valueLen];
                    System.arraycopy(tlv, 1 + lenLen + valueLen, nextTlv, 0, tlv.length - 1 - lenLen - valueLen);
                    decodeTLV(nextTlv);
                }
            } else {
                valueLen = (tlv[2] & 129) != 129 ? tlv[2] & 255 : tlv[3] & 255;
                lenLen = (tlv[2] & 129) != 129 ? 1 : 2;
                tag = new byte[2];
                value = new byte[valueLen];
                System.arraycopy(tlv, 0, tag, 0, 2);
                System.arraycopy(tlv, 2 + lenLen, value, 0, valueLen);
                mList.put(tag, value);
                Log.i(TAG, "mList_4 tag: " + BCDASCII.bytesToHexString(tag) + "  tag: " + BCDASCII.bytesToHexString(value));
                decodeTLV(value);
                if (tlv.length > 1 + lenLen + valueLen) {
                    nextTlv = new byte[tlv.length - 2 - lenLen - valueLen];
                    System.arraycopy(tlv, 2 + lenLen + valueLen, nextTlv, 0, tlv.length - 2 - lenLen - valueLen);
                    decodeTLV(nextTlv);
                }
            }

        }
    }
}
