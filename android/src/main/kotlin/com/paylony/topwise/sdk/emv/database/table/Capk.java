package com.paylony.topwise.sdk.emv.database.table;

import com.topwise.cloudpos.struct.Tlv;
import com.topwise.cloudpos.struct.TlvList;
import com.topwise.sdk.emv.database.BaseModel;
import com.topwise.sdk.emv.database.Column;
import com.topwise.sdk.emv.database.Table;

@Table(
        name = "tb_capk"
)
public class Capk extends BaseModel {
    @Column(
            name = "ridindex",
            unique = true
    )
    private String ridindex = "";
    @Column(
            name = "rid",
            style = "0,10"
    )
    private String rid = "";
    @Column(
            name = "rindex",
            style = "0,1"
    )
    private byte[] rindex = new byte[0];
    @Column(
            name = "hashInd",
            style = "0,1"
    )
    private byte[] hashInd = new byte[0];
    @Column(
            name = "arithInd",
            style = "0,1"
    )
    private byte[] arithInd = new byte[0];
    @Column(
            name = "modul",
            style = "0,248"
    )
    private byte[] modul = new byte[0];
    @Column(
            name = "exponent",
            style = "0,3"
    )
    private byte[] exponent = new byte[0];
    @Column(
            name = "expDate",
            style = "0,8"
    )
    private byte[] expDate = new byte[0];
    @Column(
            name = "checkSum",
            style = "0,32"
    )
    private byte[] checkSum = new byte[0];

    public Capk() {
    }

    public void fromTlvList(TlvList tlvList) {
        Tlv tlv = tlvList.getTlv("9F22");
        if (tlv != null) {
            this.setIndex(tlv.getValue());
        }

        tlv = tlvList.getTlv("9F06");
        if (tlv != null) {
            this.setRid(tlv.getHexValue());
        }

        tlv = tlvList.getTlv("DF06");
        if (tlv != null) {
            this.setHashInd(tlv.getValue());
        }

        tlv = tlvList.getTlv("DF07");
        if (tlv != null) {
            this.setArithInd(tlv.getValue());
        }

        tlv = tlvList.getTlv("DF02");
        if (tlv != null) {
            this.setModul(tlv.getValue());
        }

        tlv = tlvList.getTlv("DF04");
        if (tlv != null) {
            this.setExponent(tlv.getValue());
        }

        tlv = tlvList.getTlv("DF05");
        if (tlv != null) {
            this.setExpDate(tlv.getValue());
        }

        tlv = tlvList.getTlv("DF03");
        if (tlv != null) {
            this.setCheckSum(tlv.getValue());
        }

    }

    public TlvList getTlvList() {
        TlvList tlvList = new TlvList();
        tlvList.addTlv("9F06", this.rid);
        tlvList.addTlv("9F22", this.rindex);
        tlvList.addTlv("DF06", this.hashInd);
        tlvList.addTlv("DF07", this.arithInd);
        tlvList.addTlv("DF02", this.modul);
        tlvList.addTlv("DF04", this.exponent);
        tlvList.addTlv("DF05", this.expDate);
        tlvList.addTlv("DF03", this.checkSum);
        return tlvList;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
        if (rid != null && rid.length() != 0) {
            this.ridindex = (rid + Integer.toHexString(this.rindex[0] & 255)).toUpperCase();
        }

    }

    public byte getIndex() {
        return this.rindex[0];
    }

    public void setIndex(byte[] index) {
        this.rindex = index;
        if (this.rid != null && this.rid.length() != 0) {
            this.ridindex = (this.rid + Integer.toHexString(index[0] & 255)).toUpperCase();
        }

    }

    public byte getHashInd() {
        return this.hashInd[0];
    }

    public void setHashInd(byte[] hashInd) {
        this.hashInd = hashInd;
    }

    public byte getArithInd() {
        return this.arithInd[0];
    }

    public void setArithInd(byte[] arithInd) {
        this.arithInd = arithInd;
    }

    public byte[] getModul() {
        return this.modul;
    }

    public void setModul(byte[] modul) {
        this.modul = modul;
    }

    public byte[] getExponent() {
        return this.exponent;
    }

    public void setExponent(byte[] exponent) {
        this.exponent = exponent;
    }

    public byte[] getExpDate() {
        return this.expDate;
    }

    public void setExpDate(byte[] expDate) {
        this.expDate = expDate;
    }

    public byte[] getCheckSum() {
        return this.checkSum;
    }

    public void setCheckSum(byte[] checkSum) {
        this.checkSum = checkSum;
    }
}
