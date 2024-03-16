package com.paylony.topwise.emvreader.card;

import com.paylony.topwise.emvreader.Utils;
import com.paylony.topwise.emvreader.app.PosApplication;
import com.paylony.topwise.emvreader.cache.ConsumeData;
import com.topwise.sdk.emv.EmvTransData;

public class EmvTransDataSub {
    private static final String TAG = Utils.TAGPUBLIC + EmvTransDataSub.class.getSimpleName();

    private byte mTranstype;//消费0x00 查询0x31 预授权0x03 退货0x20 消费撤销0x20 ...
    private byte mCardType;//CardType.RF或CardType.IC或RF或CardType.MAG
    private EmvTransData mEmvTransData;

    public EmvTransData getEmvTransData(boolean isIc) {
        int consumeType = PosApplication.getApp().mConsumeData.getConsumeType();
        int amount = Integer.parseInt(PosApplication.getApp().mConsumeData.getAmount().replace(".", ""));
        if (consumeType == ConsumeData.CONSUME_TYPE_CASHBACK)
            mTranstype = 0x09;
        else
            mTranstype = 0x00;
        if (isIc) {
            mCardType = CardType.IC;
        } else {
            mCardType = CardType.RF;
        }
        mEmvTransData = new EmvTransData(mCardType, mTranstype, amount, true);
        return mEmvTransData;
    }
}