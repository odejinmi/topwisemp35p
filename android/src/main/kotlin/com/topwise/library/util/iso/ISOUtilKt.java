package com.topwise.library.util.iso;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jpos.iso.ISOMsg;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000*\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u001a\u0012\u0010\b\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u001a \u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r\u001a\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0011\u001a\u00020\u0006\u001a\n\u0010\u0012\u001a\u00020\u0013*\u00020\u0014\u001a\n\u0010\u0015\u001a\u00020\u0016*\u00020\u0013\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0017"},
        d2 = {"ParameterDownloadField", "", "ResponseField", "SessionIdField", "TerminalIdField", "getFirstSixDigit", "", "number", "getLastFourDigit", "leftPad", "original", "length", "padChar", "", "padRightSpacing", "count", "removeFirstSpecialCharacters", "string", "toIso", "Lorg/jpos/iso/ISOMsg;", "", "toStatus", "Lcom/topwise/library/util/iso/IsoTransactionStatus;", "app_release"}
)
public final class ISOUtilKt {
    public static final int ResponseField = 39;
    public static final int SessionIdField = 61;
    public static final int TerminalIdField = 41;
    public static final int ParameterDownloadField = 62;

    @NotNull
    public static final ISOMsg toIso(@NotNull byte[] $this$toIso) {
        Intrinsics.checkParameterIsNotNull($this$toIso, "$this$toIso");
        ISOMsg iso = new ISOMsg();
        return iso;
    }

    @NotNull
    public static final IsoTransactionStatus toStatus(@NotNull ISOMsg $this$toStatus) {
        Intrinsics.checkParameterIsNotNull($this$toStatus, "$this$toStatus");
        return $this$toStatus.getString(39).equals("00") ? IsoTransactionStatus.SUCCESS : IsoTransactionStatus.FAILED;
    }

    @Nullable
    public static final String leftPad(@NotNull String original, int length, char padChar) {
        Intrinsics.checkParameterIsNotNull(original, "original");
        StringBuilder result = new StringBuilder(original);

        while(result.length() < length) {
            result.insert(0, padChar);
        }

        return result.toString();
    }

    @Nullable
    public static final String removeFirstSpecialCharacters(@NotNull String string) {
        Intrinsics.checkParameterIsNotNull(string, "string");
        byte var2 = 1;
        boolean var3 = false;
        String var10000 = string.substring(var2);
        Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
        return var10000;
    }

    @Nullable
    public static final String padRightSpacing(int count) {
        return StringsKt.repeat((CharSequence)" ", count);
    }

    @Nullable
    public static final String getFirstSixDigit(@Nullable String number) {
        CharSequence var1 = (CharSequence)number;
        boolean var2 = false;
        boolean var3 = false;
        String var10000;
        if (var1 != null && var1.length() != 0 && number.length() > 5) {
            byte var5 = 0;
            byte var6 = 6;
            boolean var4 = false;
            if (number == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = number.substring(var5, var6);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        } else {
            var10000 = null;
        }

        return var10000;
    }

    @Nullable
    public static final String getLastFourDigit(@Nullable String number) {
        CharSequence var1 = (CharSequence)number;
        boolean var2 = false;
        boolean var3 = false;
        String var10000;
        if (var1 != null && var1.length() != 0 && number.length() > 4) {
            int var4 = number.length() - 4;
            var3 = false;
            if (number == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = number.substring(var4);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
        } else {
            var10000 = null;
        }

        return var10000;
    }
}
