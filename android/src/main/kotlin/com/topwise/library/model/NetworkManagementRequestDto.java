package com.topwise.library.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u000b\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\u000b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"},
        d2 = {"Lcom/topwise/library/model/NetworkManagementRequestDto;", "", "stan", "", "serialNumber", "(Ljava/lang/String;Ljava/lang/String;)V", "getSerialNumber", "()Ljava/lang/String;", "getStan", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}
)
public final class NetworkManagementRequestDto {
    @Nullable
    private final String stan;
    @Nullable
    private final String serialNumber;

    @Nullable
    public final String getStan() {
        return this.stan;
    }

    @Nullable
    public final String getSerialNumber() {
        return this.serialNumber;
    }

    public NetworkManagementRequestDto(@Nullable String stan, @Nullable String serialNumber) {
        this.stan = stan;
        this.serialNumber = serialNumber;
    }

    @Nullable
    public final String component1() {
        return this.stan;
    }

    @Nullable
    public final String component2() {
        return this.serialNumber;
    }

    @NotNull
    public final NetworkManagementRequestDto copy(@Nullable String stan, @Nullable String serialNumber) {
        return new NetworkManagementRequestDto(stan, serialNumber);
    }

    // $FF: synthetic method
    public static NetworkManagementRequestDto copy$default(NetworkManagementRequestDto var0, String var1, String var2, int var3, Object var4) {
        if ((var3 & 1) != 0) {
            var1 = var0.stan;
        }

        if ((var3 & 2) != 0) {
            var2 = var0.serialNumber;
        }

        return var0.copy(var1, var2);
    }

    @NotNull
    public String toString() {
        return "NetworkManagementRequestDto(stan=" + this.stan + ", serialNumber=" + this.serialNumber + ")";
    }

    public int hashCode() {
        String var10000 = this.stan;
        int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
        String var10001 = this.serialNumber;
        return var1 + (var10001 != null ? var10001.hashCode() : 0);
    }

    public boolean equals(@Nullable Object var1) {
        if (this != var1) {
            if (var1 instanceof NetworkManagementRequestDto) {
                NetworkManagementRequestDto var2 = (NetworkManagementRequestDto)var1;
                if (Intrinsics.areEqual(this.stan, var2.stan) && Intrinsics.areEqual(this.serialNumber, var2.serialNumber)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
