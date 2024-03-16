package com.topwise.library.util.emv;

import androidx.annotation.Keep;
import com.topwise.library.model.DebitCardRequestDto;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\tHÆ\u0003J3\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00072\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001d"},
        d2 = {"Lcom/topwise/library/util/emv/TransactionMonitor;", "", "state", "Lcom/topwise/library/util/emv/DeviceState;", "message", "", "status", "", "transactionData", "Lcom/topwise/library/model/DebitCardRequestDto;", "(Lcom/topwise/library/util/emv/DeviceState;Ljava/lang/String;ZLcom/topwise/library/model/DebitCardRequestDto;)V", "getMessage", "()Ljava/lang/String;", "getState", "()Lcom/topwise/library/util/emv/DeviceState;", "getStatus", "()Z", "getTransactionData", "()Lcom/topwise/library/model/DebitCardRequestDto;", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_release"}
)
@Keep
public final class TransactionMonitor {
    @NotNull
    private final DeviceState state;
    @NotNull
    private final String message;
    private final boolean status;
    @Nullable
    private final DebitCardRequestDto transactionData;

    @NotNull
    public final DeviceState getState() {
        return this.state;
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    public final boolean getStatus() {
        return this.status;
    }

    @Nullable
    public final DebitCardRequestDto getTransactionData() {
        return this.transactionData;
    }

    public TransactionMonitor(@NotNull DeviceState state, @NotNull String message, boolean status, @Nullable DebitCardRequestDto transactionData) {
//        Intrinsics.checkParameterIsNotNull(state, "state");
//        Intrinsics.checkParameterIsNotNull(message, "message");
        super();
        this.state = state;
        this.message = message;
        this.status = status;
        this.transactionData = transactionData;
    }

    @NotNull
    public final DeviceState component1() {
        return this.state;
    }

    @NotNull
    public final String component2() {
        return this.message;
    }

    public final boolean component3() {
        return this.status;
    }

    @Nullable
    public final DebitCardRequestDto component4() {
        return this.transactionData;
    }

    @NotNull
    public final TransactionMonitor copy(@NotNull DeviceState state, @NotNull String message, boolean status, @Nullable DebitCardRequestDto transactionData) {
        Intrinsics.checkParameterIsNotNull(state, "state");
        Intrinsics.checkParameterIsNotNull(message, "message");
        return new TransactionMonitor(state, message, status, transactionData);
    }

    // $FF: synthetic method
    public static TransactionMonitor copy$default(TransactionMonitor var0, DeviceState var1, String var2, boolean var3, DebitCardRequestDto var4, int var5, Object var6) {
        if ((var5 & 1) != 0) {
            var1 = var0.state;
        }

        if ((var5 & 2) != 0) {
            var2 = var0.message;
        }

        if ((var5 & 4) != 0) {
            var3 = var0.status;
        }

        if ((var5 & 8) != 0) {
            var4 = var0.transactionData;
        }

        return var0.copy(var1, var2, var3, var4);
    }

    @NotNull
    public String toString() {
        return "TransactionMonitor(state=" + this.state + ", message=" + this.message + ", status=" + this.status + ", transactionData=" + this.transactionData + ")";
    }

    public int hashCode() {
        DeviceState var10000 = this.state;
        int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
        String var10001 = this.message;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        byte var2 = this.status ? (byte) 1 : (byte) 0;
        if (var2 != 0) {
            var2 = 1;
        }

        var1 = (var1 + var2) * 31;
        DebitCardRequestDto var3 = this.transactionData;
        return var1 + (var3 != null ? var3.hashCode() : 0);
    }

    public boolean equals(@Nullable Object var1) {
        if (this != var1) {
            if (var1 instanceof TransactionMonitor) {
                TransactionMonitor var2 = (TransactionMonitor)var1;
                if (Intrinsics.areEqual(this.state, var2.state) && Intrinsics.areEqual(this.message, var2.message) && this.status == var2.status && Intrinsics.areEqual(this.transactionData, var2.transactionData)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
