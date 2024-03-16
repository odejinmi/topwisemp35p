package com.topwise.library.model;

import java.math.BigDecimal;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u001d"},
        d2 = {"Lcom/topwise/library/model/DebitCardResponse;", "", "rrn", "", "stan", "terminalId", "amount", "Ljava/math/BigDecimal;", "responseCode", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/math/BigDecimal;Ljava/lang/String;)V", "getAmount", "()Ljava/math/BigDecimal;", "getResponseCode", "()Ljava/lang/String;", "getRrn", "getStan", "getTerminalId", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}
)
public final class DebitCardResponse {
    @NotNull
    private final String rrn;
    @NotNull
    private final String stan;
    @NotNull
    private final String terminalId;
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final String responseCode;

    @NotNull
    public final String getRrn() {
        return this.rrn;
    }

    @NotNull
    public final String getStan() {
        return this.stan;
    }

    @NotNull
    public final String getTerminalId() {
        return this.terminalId;
    }

    @NotNull
    public final BigDecimal getAmount() {
        return this.amount;
    }

    @NotNull
    public final String getResponseCode() {
        return this.responseCode;
    }

    public DebitCardResponse(@NotNull String rrn, @NotNull String stan, @NotNull String terminalId, @NotNull BigDecimal amount, @NotNull String responseCode) {
//        Intrinsics.checkParameterIsNotNull(rrn, "rrn");
//        Intrinsics.checkParameterIsNotNull(stan, "stan");
//        Intrinsics.checkParameterIsNotNull(terminalId, "terminalId");
//        Intrinsics.checkParameterIsNotNull(amount, "amount");
//        Intrinsics.checkParameterIsNotNull(responseCode, "responseCode");
        super();
        this.rrn = rrn;
        this.stan = stan;
        this.terminalId = terminalId;
        this.amount = amount;
        this.responseCode = responseCode;
    }

    @NotNull
    public final String component1() {
        return this.rrn;
    }

    @NotNull
    public final String component2() {
        return this.stan;
    }

    @NotNull
    public final String component3() {
        return this.terminalId;
    }

    @NotNull
    public final BigDecimal component4() {
        return this.amount;
    }

    @NotNull
    public final String component5() {
        return this.responseCode;
    }

    @NotNull
    public final DebitCardResponse copy(@NotNull String rrn, @NotNull String stan, @NotNull String terminalId, @NotNull BigDecimal amount, @NotNull String responseCode) {
        Intrinsics.checkParameterIsNotNull(rrn, "rrn");
        Intrinsics.checkParameterIsNotNull(stan, "stan");
        Intrinsics.checkParameterIsNotNull(terminalId, "terminalId");
        Intrinsics.checkParameterIsNotNull(amount, "amount");
        Intrinsics.checkParameterIsNotNull(responseCode, "responseCode");
        return new DebitCardResponse(rrn, stan, terminalId, amount, responseCode);
    }

    // $FF: synthetic method
    public static DebitCardResponse copy$default(DebitCardResponse var0, String var1, String var2, String var3, BigDecimal var4, String var5, int var6, Object var7) {
        if ((var6 & 1) != 0) {
            var1 = var0.rrn;
        }

        if ((var6 & 2) != 0) {
            var2 = var0.stan;
        }

        if ((var6 & 4) != 0) {
            var3 = var0.terminalId;
        }

        if ((var6 & 8) != 0) {
            var4 = var0.amount;
        }

        if ((var6 & 16) != 0) {
            var5 = var0.responseCode;
        }

        return var0.copy(var1, var2, var3, var4, var5);
    }

    @NotNull
    public String toString() {
        return "DebitCardResponse(rrn=" + this.rrn + ", stan=" + this.stan + ", terminalId=" + this.terminalId + ", amount=" + this.amount + ", responseCode=" + this.responseCode + ")";
    }

    public int hashCode() {
        String var10000 = this.rrn;
        int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
        String var10001 = this.stan;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.terminalId;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        BigDecimal var2 = this.amount;
        var1 = (var1 + (var2 != null ? var2.hashCode() : 0)) * 31;
        var10001 = this.responseCode;
        return var1 + (var10001 != null ? var10001.hashCode() : 0);
    }

    public boolean equals(@Nullable Object var1) {
        if (this != var1) {
            if (var1 instanceof DebitCardResponse) {
                DebitCardResponse var2 = (DebitCardResponse)var1;
                if (Intrinsics.areEqual(this.rrn, var2.rrn) && Intrinsics.areEqual(this.stan, var2.stan) && Intrinsics.areEqual(this.terminalId, var2.terminalId) && Intrinsics.areEqual(this.amount, var2.amount) && Intrinsics.areEqual(this.responseCode, var2.responseCode)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
