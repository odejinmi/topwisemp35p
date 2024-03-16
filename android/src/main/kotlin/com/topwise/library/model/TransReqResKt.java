package com.topwise.library.model;

import com.topwise.library.util.TerminalUtils;
import com.topwise.library.util.iso.ISOUtilKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"},
        d2 = {"toIso", "Lorg/jpos/iso/ISOMsg;", "Lcom/topwise/library/model/DebitCardRequestDto;", "app_release"}
)
public final class TransReqResKt {
    @NotNull
    public static final ISOMsg toIso(@NotNull DebitCardRequestDto $this$toIso) {
        Intrinsics.checkParameterIsNotNull($this$toIso, "$this$toIso");
        ISOMsg iso = new ISOMsg();
        try {
            iso.setMTI("0200");
        } catch (ISOException e) {
            throw new RuntimeException(e);
        }
        iso.set(2, $this$toIso.getPan());
        iso.set(3, $this$toIso.getAccountType().getCode());
        String var10002 = $this$toIso.getAmount().toPlainString();
        Intrinsics.checkExpressionValueIsNotNull(var10002, "amount.toPlainString()");
        iso.set(4, ISOUtilKt.leftPad(var10002, 12, '0'));
        iso.set(11, $this$toIso.getStan());
        iso.set(14, $this$toIso.getCardExpiryDate());
        iso.set(23, $this$toIso.getSequenceNumber());
        iso.set(25, "00");
        iso.set(32, $this$toIso.getAcquiringInstitutionalCode());
        iso.set(35, $this$toIso.getTrack2Data());
        iso.set(37, $this$toIso.getRrn());
        iso.set(40, $this$toIso.getServiceCode());
        iso.set(41, $this$toIso.getTerminalId());
        iso.set(42, $this$toIso.getAcceptorCode());
        iso.set(43, $this$toIso.getMerchantName());
        iso.set(49, $this$toIso.getTransactionCode());
        CharSequence var2 = (CharSequence)$this$toIso.getPinBlock();
        boolean var3 = false;
        boolean var4 = false;
        if (var2 != null && var2.length() != 0) {
            iso.set(52, $this$toIso.getPinBlock());
        }

        iso.set(55, $this$toIso.getIccData());
        iso.set(59, $this$toIso.getCustomerName());
        iso.set(123, $this$toIso.getPostDataCode());
        TerminalUtils.Companion.logISOMsg(iso);
        return iso;
    }
}
