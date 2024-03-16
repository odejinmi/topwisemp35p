package com.topwise.library.model;

import androidx.annotation.Keep;
import java.math.BigDecimal;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b*\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B£\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u00104\u001a\u00020\u0003HÆ\u0003J\t\u00105\u001a\u00020\u0014HÆ\u0003J\t\u00106\u001a\u00020\u0016HÆ\u0003J\t\u00107\u001a\u00020\u0003HÆ\u0003J\t\u00108\u001a\u00020\u0003HÆ\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010:\u001a\u00020\u0003HÆ\u0003J\t\u0010;\u001a\u00020\u0003HÆ\u0003J\t\u0010<\u001a\u00020\u0003HÆ\u0003J\t\u0010=\u001a\u00020\u0003HÆ\u0003J\t\u0010>\u001a\u00020\u0003HÆ\u0003JÁ\u0001\u0010?\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0016HÆ\u0001J\u0013\u0010@\u001a\u00020A2\b\u0010B\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010C\u001a\u00020DHÖ\u0001J\t\u0010E\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0011\u0010\u0010\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0019R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0019R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0019R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0019R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0019¨\u0006F"},
        d2 = {"Lcom/topwise/library/model/DebitCardRequestDto;", "", "pan", "", "stan", "rrn", "pinBlock", "iccData", "track2Data", "postDataCode", "cardExpiryDate", "acceptorCode", "sequenceNumber", "serviceCode", "transactionCode", "terminalId", "merchantName", "customerName", "acquiringInstitutionalCode", "amount", "Ljava/math/BigDecimal;", "accountType", "Lcom/topwise/library/model/CardAccountType;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/math/BigDecimal;Lcom/topwise/library/model/CardAccountType;)V", "getAcceptorCode", "()Ljava/lang/String;", "getAccountType", "()Lcom/topwise/library/model/CardAccountType;", "getAcquiringInstitutionalCode", "getAmount", "()Ljava/math/BigDecimal;", "getCardExpiryDate", "getCustomerName", "getIccData", "getMerchantName", "getPan", "getPinBlock", "getPostDataCode", "getRrn", "getSequenceNumber", "getServiceCode", "getStan", "getTerminalId", "getTrack2Data", "getTransactionCode", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}
)
@Keep
public final class DebitCardRequestDto {
    @NotNull
    private final String pan;
    @NotNull
    private final String stan;
    @NotNull
    private final String rrn;
    @Nullable
    private final String pinBlock;
    @NotNull
    private final String iccData;
    @NotNull
    private final String track2Data;
    @NotNull
    private final String postDataCode;
    @NotNull
    private final String cardExpiryDate;
    @NotNull
    private final String acceptorCode;
    @NotNull
    private final String sequenceNumber;
    @NotNull
    private final String serviceCode;
    @NotNull
    private final String transactionCode;
    @NotNull
    private final String terminalId;
    @NotNull
    private final String merchantName;
    @Nullable
    private final String customerName;
    @NotNull
    private final String acquiringInstitutionalCode;
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final CardAccountType accountType;

    @NotNull
    public final String getPan() {
        return this.pan;
    }

    @NotNull
    public final String getStan() {
        return this.stan;
    }

    @NotNull
    public final String getRrn() {
        return this.rrn;
    }

    @Nullable
    public final String getPinBlock() {
        return this.pinBlock;
    }

    @NotNull
    public final String getIccData() {
        return this.iccData;
    }

    @NotNull
    public final String getTrack2Data() {
        return this.track2Data;
    }

    @NotNull
    public final String getPostDataCode() {
        return this.postDataCode;
    }

    @NotNull
    public final String getCardExpiryDate() {
        return this.cardExpiryDate;
    }

    @NotNull
    public final String getAcceptorCode() {
        return this.acceptorCode;
    }

    @NotNull
    public final String getSequenceNumber() {
        return this.sequenceNumber;
    }

    @NotNull
    public final String getServiceCode() {
        return this.serviceCode;
    }

    @NotNull
    public final String getTransactionCode() {
        return this.transactionCode;
    }

    @NotNull
    public final String getTerminalId() {
        return this.terminalId;
    }

    @NotNull
    public final String getMerchantName() {
        return this.merchantName;
    }

    @Nullable
    public final String getCustomerName() {
        return this.customerName;
    }

    @NotNull
    public final String getAcquiringInstitutionalCode() {
        return this.acquiringInstitutionalCode;
    }

    @NotNull
    public final BigDecimal getAmount() {
        return this.amount;
    }

    @NotNull
    public final CardAccountType getAccountType() {
        return this.accountType;
    }

    public DebitCardRequestDto(@NotNull String pan, @NotNull String stan, @NotNull String rrn, @Nullable String pinBlock, @NotNull String iccData, @NotNull String track2Data, @NotNull String postDataCode, @NotNull String cardExpiryDate, @NotNull String acceptorCode, @NotNull String sequenceNumber, @NotNull String serviceCode, @NotNull String transactionCode, @NotNull String terminalId, @NotNull String merchantName, @Nullable String customerName, @NotNull String acquiringInstitutionalCode, @NotNull BigDecimal amount, @NotNull CardAccountType accountType) {
//        Intrinsics.checkParameterIsNotNull(pan, "pan");
//        Intrinsics.checkParameterIsNotNull(stan, "stan");
//        Intrinsics.checkParameterIsNotNull(rrn, "rrn");
//        Intrinsics.checkParameterIsNotNull(iccData, "iccData");
//        Intrinsics.checkParameterIsNotNull(track2Data, "track2Data");
//        Intrinsics.checkParameterIsNotNull(postDataCode, "postDataCode");
//        Intrinsics.checkParameterIsNotNull(cardExpiryDate, "cardExpiryDate");
//        Intrinsics.checkParameterIsNotNull(acceptorCode, "acceptorCode");
//        Intrinsics.checkParameterIsNotNull(sequenceNumber, "sequenceNumber");
//        Intrinsics.checkParameterIsNotNull(serviceCode, "serviceCode");
//        Intrinsics.checkParameterIsNotNull(transactionCode, "transactionCode");
//        Intrinsics.checkParameterIsNotNull(terminalId, "terminalId");
//        Intrinsics.checkParameterIsNotNull(merchantName, "merchantName");
//        Intrinsics.checkParameterIsNotNull(acquiringInstitutionalCode, "acquiringInstitutionalCode");
//        Intrinsics.checkParameterIsNotNull(amount, "amount");
//        Intrinsics.checkParameterIsNotNull(accountType, "accountType");
        super();
        this.pan = pan;
        this.stan = stan;
        this.rrn = rrn;
        this.pinBlock = pinBlock;
        this.iccData = iccData;
        this.track2Data = track2Data;
        this.postDataCode = postDataCode;
        this.cardExpiryDate = cardExpiryDate;
        this.acceptorCode = acceptorCode;
        this.sequenceNumber = sequenceNumber;
        this.serviceCode = serviceCode;
        this.transactionCode = transactionCode;
        this.terminalId = terminalId;
        this.merchantName = merchantName;
        this.customerName = customerName;
        this.acquiringInstitutionalCode = acquiringInstitutionalCode;
        this.amount = amount;
        this.accountType = accountType;
    }

    // $FF: synthetic method
    public DebitCardRequestDto(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, BigDecimal var17, CardAccountType var18, int var19, DefaultConstructorMarker var20) {
//        if ((var19 & 8) != 0) {
//            var4 = (String)null;
//        }
//
//        if ((var19 & 1024) != 0) {
//            var11 = "221";
//        }
//
//        if ((var19 & 2048) != 0) {
//            var12 = "566";
//        }
//
//        if ((var19 & 16384) != 0) {
//            var15 = (String)null;
//        }
//
//        if ((var19 & 131072) != 0) {
//            var18 = CardAccountType.SAVINGS;
//        }

        this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18);
    }

    @NotNull
    public final String component1() {
        return this.pan;
    }

    @NotNull
    public final String component2() {
        return this.stan;
    }

    @NotNull
    public final String component3() {
        return this.rrn;
    }

    @Nullable
    public final String component4() {
        return this.pinBlock;
    }

    @NotNull
    public final String component5() {
        return this.iccData;
    }

    @NotNull
    public final String component6() {
        return this.track2Data;
    }

    @NotNull
    public final String component7() {
        return this.postDataCode;
    }

    @NotNull
    public final String component8() {
        return this.cardExpiryDate;
    }

    @NotNull
    public final String component9() {
        return this.acceptorCode;
    }

    @NotNull
    public final String component10() {
        return this.sequenceNumber;
    }

    @NotNull
    public final String component11() {
        return this.serviceCode;
    }

    @NotNull
    public final String component12() {
        return this.transactionCode;
    }

    @NotNull
    public final String component13() {
        return this.terminalId;
    }

    @NotNull
    public final String component14() {
        return this.merchantName;
    }

    @Nullable
    public final String component15() {
        return this.customerName;
    }

    @NotNull
    public final String component16() {
        return this.acquiringInstitutionalCode;
    }

    @NotNull
    public final BigDecimal component17() {
        return this.amount;
    }

    @NotNull
    public final CardAccountType component18() {
        return this.accountType;
    }

    @NotNull
    public final DebitCardRequestDto copy(@NotNull String pan, @NotNull String stan, @NotNull String rrn, @Nullable String pinBlock, @NotNull String iccData, @NotNull String track2Data, @NotNull String postDataCode, @NotNull String cardExpiryDate, @NotNull String acceptorCode, @NotNull String sequenceNumber, @NotNull String serviceCode, @NotNull String transactionCode, @NotNull String terminalId, @NotNull String merchantName, @Nullable String customerName, @NotNull String acquiringInstitutionalCode, @NotNull BigDecimal amount, @NotNull CardAccountType accountType) {
        Intrinsics.checkParameterIsNotNull(pan, "pan");
        Intrinsics.checkParameterIsNotNull(stan, "stan");
        Intrinsics.checkParameterIsNotNull(rrn, "rrn");
        Intrinsics.checkParameterIsNotNull(iccData, "iccData");
        Intrinsics.checkParameterIsNotNull(track2Data, "track2Data");
        Intrinsics.checkParameterIsNotNull(postDataCode, "postDataCode");
        Intrinsics.checkParameterIsNotNull(cardExpiryDate, "cardExpiryDate");
        Intrinsics.checkParameterIsNotNull(acceptorCode, "acceptorCode");
        Intrinsics.checkParameterIsNotNull(sequenceNumber, "sequenceNumber");
        Intrinsics.checkParameterIsNotNull(serviceCode, "serviceCode");
        Intrinsics.checkParameterIsNotNull(transactionCode, "transactionCode");
        Intrinsics.checkParameterIsNotNull(terminalId, "terminalId");
        Intrinsics.checkParameterIsNotNull(merchantName, "merchantName");
        Intrinsics.checkParameterIsNotNull(acquiringInstitutionalCode, "acquiringInstitutionalCode");
        Intrinsics.checkParameterIsNotNull(amount, "amount");
        Intrinsics.checkParameterIsNotNull(accountType, "accountType");
        return new DebitCardRequestDto(pan, stan, rrn, pinBlock, iccData, track2Data, postDataCode, cardExpiryDate, acceptorCode, sequenceNumber, serviceCode, transactionCode, terminalId, merchantName, customerName, acquiringInstitutionalCode, amount, accountType);
    }

    // $FF: synthetic method
    public static DebitCardRequestDto copy$default(DebitCardRequestDto var0, String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, BigDecimal var17, CardAccountType var18, int var19, Object var20) {
        if ((var19 & 1) != 0) {
            var1 = var0.pan;
        }

        if ((var19 & 2) != 0) {
            var2 = var0.stan;
        }

        if ((var19 & 4) != 0) {
            var3 = var0.rrn;
        }

        if ((var19 & 8) != 0) {
            var4 = var0.pinBlock;
        }

        if ((var19 & 16) != 0) {
            var5 = var0.iccData;
        }

        if ((var19 & 32) != 0) {
            var6 = var0.track2Data;
        }

        if ((var19 & 64) != 0) {
            var7 = var0.postDataCode;
        }

        if ((var19 & 128) != 0) {
            var8 = var0.cardExpiryDate;
        }

        if ((var19 & 256) != 0) {
            var9 = var0.acceptorCode;
        }

        if ((var19 & 512) != 0) {
            var10 = var0.sequenceNumber;
        }

        if ((var19 & 1024) != 0) {
            var11 = var0.serviceCode;
        }

        if ((var19 & 2048) != 0) {
            var12 = var0.transactionCode;
        }

        if ((var19 & 4096) != 0) {
            var13 = var0.terminalId;
        }

        if ((var19 & 8192) != 0) {
            var14 = var0.merchantName;
        }

        if ((var19 & 16384) != 0) {
            var15 = var0.customerName;
        }

        if ((var19 & '耀') != 0) {
            var16 = var0.acquiringInstitutionalCode;
        }

        if ((var19 & 65536) != 0) {
            var17 = var0.amount;
        }

        if ((var19 & 131072) != 0) {
            var18 = var0.accountType;
        }

        return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18);
    }

    @NotNull
    public String toString() {
        return "DebitCardRequestDto(pan=" + this.pan + ", stan=" + this.stan + ", rrn=" + this.rrn + ", pinBlock=" + this.pinBlock + ", iccData=" + this.iccData + ", track2Data=" + this.track2Data + ", postDataCode=" + this.postDataCode + ", cardExpiryDate=" + this.cardExpiryDate + ", acceptorCode=" + this.acceptorCode + ", sequenceNumber=" + this.sequenceNumber + ", serviceCode=" + this.serviceCode + ", transactionCode=" + this.transactionCode + ", terminalId=" + this.terminalId + ", merchantName=" + this.merchantName + ", customerName=" + this.customerName + ", acquiringInstitutionalCode=" + this.acquiringInstitutionalCode + ", amount=" + this.amount + ", accountType=" + this.accountType + ")";
    }

    public int hashCode() {
        String var10000 = this.pan;
        int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
        String var10001 = this.stan;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.rrn;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.pinBlock;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.iccData;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.track2Data;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.postDataCode;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.cardExpiryDate;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.acceptorCode;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.sequenceNumber;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.serviceCode;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.transactionCode;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.terminalId;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.merchantName;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.customerName;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.acquiringInstitutionalCode;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        BigDecimal var2 = this.amount;
        var1 = (var1 + (var2 != null ? var2.hashCode() : 0)) * 31;
        CardAccountType var3 = this.accountType;
        return var1 + (var3 != null ? var3.hashCode() : 0);
    }

    public boolean equals(@Nullable Object var1) {
        if (this != var1) {
            if (var1 instanceof DebitCardRequestDto) {
                DebitCardRequestDto var2 = (DebitCardRequestDto)var1;
                if (Intrinsics.areEqual(this.pan, var2.pan) && Intrinsics.areEqual(this.stan, var2.stan) && Intrinsics.areEqual(this.rrn, var2.rrn) && Intrinsics.areEqual(this.pinBlock, var2.pinBlock) && Intrinsics.areEqual(this.iccData, var2.iccData) && Intrinsics.areEqual(this.track2Data, var2.track2Data) && Intrinsics.areEqual(this.postDataCode, var2.postDataCode) && Intrinsics.areEqual(this.cardExpiryDate, var2.cardExpiryDate) && Intrinsics.areEqual(this.acceptorCode, var2.acceptorCode) && Intrinsics.areEqual(this.sequenceNumber, var2.sequenceNumber) && Intrinsics.areEqual(this.serviceCode, var2.serviceCode) && Intrinsics.areEqual(this.transactionCode, var2.transactionCode) && Intrinsics.areEqual(this.terminalId, var2.terminalId) && Intrinsics.areEqual(this.merchantName, var2.merchantName) && Intrinsics.areEqual(this.customerName, var2.customerName) && Intrinsics.areEqual(this.acquiringInstitutionalCode, var2.acquiringInstitutionalCode) && Intrinsics.areEqual(this.amount, var2.amount) && Intrinsics.areEqual(this.accountType, var2.accountType)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
