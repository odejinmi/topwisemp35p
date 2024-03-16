package com.topwise.library.util.emv;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Keep;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.android.parcel.Parcelize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J8\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007HÆ\u0001¢\u0006\u0002\u0010\u0017J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0007HÖ\u0001J\u0019\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006%"},
        d2 = {"Lcom/topwise/library/util/emv/TransactionData;", "Landroid/os/Parcelable;", "transDate", "Ljava/util/Date;", "transAmount", "", "cardType", "", "cardLast4Digit", "(Ljava/util/Date;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;)V", "getCardLast4Digit", "()Ljava/lang/String;", "getCardType", "getTransAmount", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getTransDate", "()Ljava/util/Date;", "component1", "component2", "component3", "component4", "copy", "(Ljava/util/Date;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;)Lcom/topwise/library/util/emv/TransactionData;", "describeContents", "", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_release"}
)
@Keep
@Parcelize
public final class TransactionData implements Parcelable {
    @NotNull
    private final Date transDate;
    @Nullable
    private final Long transAmount;
    @NotNull
    private final String cardType;
    @NotNull
    private final String cardLast4Digit;
    public static final Parcelable.Creator CREATOR = new Creator();

    @NotNull
    public final Date getTransDate() {
        return this.transDate;
    }

    @Nullable
    public final Long getTransAmount() {
        return this.transAmount;
    }

    @NotNull
    public final String getCardType() {
        return this.cardType;
    }

    @NotNull
    public final String getCardLast4Digit() {
        return this.cardLast4Digit;
    }

    public TransactionData(@NotNull Date transDate, @Nullable Long transAmount, @NotNull String cardType, @NotNull String cardLast4Digit) {
//        Intrinsics.checkParameterIsNotNull(transDate, "transDate");
//        Intrinsics.checkParameterIsNotNull(cardType, "cardType");
//        Intrinsics.checkParameterIsNotNull(cardLast4Digit, "cardLast4Digit");
        super();
        this.transDate = transDate;
        this.transAmount = transAmount;
        this.cardType = cardType;
        this.cardLast4Digit = cardLast4Digit;
    }

    @NotNull
    public final Date component1() {
        return this.transDate;
    }

    @Nullable
    public final Long component2() {
        return this.transAmount;
    }

    @NotNull
    public final String component3() {
        return this.cardType;
    }

    @NotNull
    public final String component4() {
        return this.cardLast4Digit;
    }

    @NotNull
    public final TransactionData copy(@NotNull Date transDate, @Nullable Long transAmount, @NotNull String cardType, @NotNull String cardLast4Digit) {
        Intrinsics.checkParameterIsNotNull(transDate, "transDate");
        Intrinsics.checkParameterIsNotNull(cardType, "cardType");
        Intrinsics.checkParameterIsNotNull(cardLast4Digit, "cardLast4Digit");
        return new TransactionData(transDate, transAmount, cardType, cardLast4Digit);
    }

    // $FF: synthetic method
    public static TransactionData copy$default(TransactionData var0, Date var1, Long var2, String var3, String var4, int var5, Object var6) {
        if ((var5 & 1) != 0) {
            var1 = var0.transDate;
        }

        if ((var5 & 2) != 0) {
            var2 = var0.transAmount;
        }

        if ((var5 & 4) != 0) {
            var3 = var0.cardType;
        }

        if ((var5 & 8) != 0) {
            var4 = var0.cardLast4Digit;
        }

        return var0.copy(var1, var2, var3, var4);
    }

    @NotNull
    public String toString() {
        return "TransactionData(transDate=" + this.transDate + ", transAmount=" + this.transAmount + ", cardType=" + this.cardType + ", cardLast4Digit=" + this.cardLast4Digit + ")";
    }

    public int hashCode() {
        Date var10000 = this.transDate;
        int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
        Long var10001 = this.transAmount;
        var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
        String var2 = this.cardType;
        var1 = (var1 + (var2 != null ? var2.hashCode() : 0)) * 31;
        var2 = this.cardLast4Digit;
        return var1 + (var2 != null ? var2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object var1) {
        if (this != var1) {
            if (var1 instanceof TransactionData) {
                TransactionData var2 = (TransactionData)var1;
                if (Intrinsics.areEqual(this.transDate, var2.transDate) && Intrinsics.areEqual(this.transAmount, var2.transAmount) && Intrinsics.areEqual(this.cardType, var2.cardType) && Intrinsics.areEqual(this.cardLast4Digit, var2.cardLast4Digit)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NotNull Parcel parcel, int flags) {
        Intrinsics.checkParameterIsNotNull(parcel, "parcel");
        parcel.writeSerializable(this.transDate);
        Long var10001 = this.transAmount;
        if (var10001 != null) {
            parcel.writeInt(1);
            parcel.writeLong(var10001);
        } else {
            parcel.writeInt(0);
        }

        parcel.writeString(this.cardType);
        parcel.writeString(this.cardLast4Digit);
    }

    @Metadata(
            mv = {1, 1, 16},
            bv = {1, 0, 3},
            k = 3
    )
    public static class Creator implements Parcelable.Creator {
        @NotNull
        public final Object[] newArray(int size) {
            return new TransactionData[size];
        }

        @NotNull
        public final Object createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkParameterIsNotNull(in, "in");
            return new TransactionData((Date)in.readSerializable(), in.readInt() != 0 ? in.readLong() : null, in.readString(), in.readString());
        }
    }
}
