package com.topwise.library.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J?\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"},
        d2 = {"Lcom/topwise/library/model/NetworkManagementResponse;", "", "merchantName", "", "ptsp", "merchantId", "terminalId", "sessionId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getMerchantId", "()Ljava/lang/String;", "getMerchantName", "getPtsp", "getSessionId", "getTerminalId", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}
)
public final class NetworkManagementResponse {
    @NotNull
    private final String merchantName;
    @NotNull
    private final String ptsp;
    @NotNull
    private final String merchantId;
    @Nullable
    private final String terminalId;
    @Nullable
    private final String sessionId;

    @NotNull
    public final String getMerchantName() {
        return this.merchantName;
    }

    @NotNull
    public final String getPtsp() {
        return this.ptsp;
    }

    @NotNull
    public final String getMerchantId() {
        return this.merchantId;
    }

    @Nullable
    public final String getTerminalId() {
        return this.terminalId;
    }

    @Nullable
    public final String getSessionId() {
        return this.sessionId;
    }

    public NetworkManagementResponse(@NotNull String merchantName, @NotNull String ptsp, @NotNull String merchantId, @Nullable String terminalId, @Nullable String sessionId) {
//        Intrinsics.checkParameterIsNotNull(merchantName, "merchantName");
//        Intrinsics.checkParameterIsNotNull(ptsp, "ptsp");
//        Intrinsics.checkParameterIsNotNull(merchantId, "merchantId");
        super();
        this.merchantName = merchantName;
        this.ptsp = ptsp;
        this.merchantId = merchantId;
        this.terminalId = terminalId;
        this.sessionId = sessionId;
    }

    // $FF: synthetic method
    public NetworkManagementResponse(String var1, String var2, String var3, String var4, String var5, int var6, DefaultConstructorMarker var7) {
//        if ((var6 & 8) != 0) {
//            var4 = (String)null;
//        }
//
//        if ((var6 & 16) != 0) {
//            var5 = (String)null;
//        }

        this(var1, var2, var3, var4, var5);
    }

    @NotNull
    public final String component1() {
        return this.merchantName;
    }

    @NotNull
    public final String component2() {
        return this.ptsp;
    }

    @NotNull
    public final String component3() {
        return this.merchantId;
    }

    @Nullable
    public final String component4() {
        return this.terminalId;
    }

    @Nullable
    public final String component5() {
        return this.sessionId;
    }

    @NotNull
    public final NetworkManagementResponse copy(@NotNull String merchantName, @NotNull String ptsp, @NotNull String merchantId, @Nullable String terminalId, @Nullable String sessionId) {
        Intrinsics.checkParameterIsNotNull(merchantName, "merchantName");
        Intrinsics.checkParameterIsNotNull(ptsp, "ptsp");
        Intrinsics.checkParameterIsNotNull(merchantId, "merchantId");
        return new NetworkManagementResponse(merchantName, ptsp, merchantId, terminalId, sessionId);
    }

    // $FF: synthetic method
    public static NetworkManagementResponse copy$default(NetworkManagementResponse var0, String var1, String var2, String var3, String var4, String var5, int var6, Object var7) {
        if ((var6 & 1) != 0) {
            var1 = var0.merchantName;
        }

        if ((var6 & 2) != 0) {
            var2 = var0.ptsp;
        }

        if ((var6 & 4) != 0) {
            var3 = var0.merchantId;
        }

        if ((var6 & 8) != 0) {
            var4 = var0.terminalId;
        }

        if ((var6 & 16) != 0) {
            var5 = var0.sessionId;
        }

        return var0.copy(var1, var2, var3, var4, var5);
    }

    @NotNull
    public String toString() {
        return "NetworkManagementResponse(merchantName=" + this.merchantName + ", ptsp=" + this.ptsp + ", merchantId=" + this.merchantId + ", terminalId=" + this.terminalId + ", sessionId=" + this.sessionId + ")";
    }

    public int hashCode() {
        String var10000 = this.merchantName;
        int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
        String var10001 = this.ptsp;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.merchantId;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.terminalId;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        var10001 = this.sessionId;
        return var1 + (var10001 != null ? var10001.hashCode() : 0);
    }

    public boolean equals(@Nullable Object var1) {
        if (this != var1) {
            if (var1 instanceof NetworkManagementResponse) {
                NetworkManagementResponse var2 = (NetworkManagementResponse)var1;
                if (Intrinsics.areEqual(this.merchantName, var2.merchantName) && Intrinsics.areEqual(this.ptsp, var2.ptsp) && Intrinsics.areEqual(this.merchantId, var2.merchantId) && Intrinsics.areEqual(this.terminalId, var2.terminalId) && Intrinsics.areEqual(this.sessionId, var2.sessionId)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
