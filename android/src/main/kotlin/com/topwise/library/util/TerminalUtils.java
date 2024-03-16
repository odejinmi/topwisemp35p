package com.topwise.library.util;

import java.nio.charset.Charset;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import timber.log.Timber;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"},
        d2 = {"Lcom/topwise/library/util/TerminalUtils;", "", "()V", "Companion", "app_release"}
)
public final class TerminalUtils {
    public static final Companion Companion = new Companion((DefaultConstructorMarker)null);

    @Metadata(
            mv = {1, 1, 16},
            bv = {1, 0, 3},
            k = 1,
            d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\n"},
            d2 = {"Lcom/topwise/library/util/TerminalUtils$Companion;", "", "()V", "appendLengthBytes", "", "raw", "logISOMsg", "", "msg", "Lorg/jpos/iso/ISOMsg;", "app_release"}
    )
    public static final class Companion {
        public final void logISOMsg(@NotNull ISOMsg msg) {
            Intrinsics.checkParameterIsNotNull(msg, "msg");
            Timber.e(":::  ----  ISO MESSAGE-----  :::");

            try {
                Timber.e("::: MTI [{}]::: msg.mti");
                int i = 1;
                int var3 = msg.getMaxField();
                if (i <= var3) {
                    while(true) {
                        if (msg.hasField(i)) {
                            Timber.e("::: Field [" + i + "] : [" + msg.getString(i) + "] :::", new Object[0]);
                        }

                        if (i == var3) {
                            break;
                        }

                        ++i;
                    }
                }
            } finally {
                Timber.e(":::---------------------------- :::");
            }

        }

        @NotNull
        public final byte[] appendLengthBytes(@NotNull byte[] raw) {
            Intrinsics.checkParameterIsNotNull(raw, "raw");
            boolean var3 = false;
            String content = new String(raw, Charsets.UTF_8);
            int x = content.length();
            String binlng = Integer.toBinaryString(x);
            Intrinsics.checkExpressionValueIsNotNull(binlng, "binlng");
            int i = 2;
            boolean var8 = false;
            String headerLength = Integer.toHexString(Integer.parseInt(binlng, CharsKt.checkRadix(i)));
            StringCompanionObject var6 = StringCompanionObject.INSTANCE;
            String var13 = "%4s";
            Object[] var14 = new Object[]{headerLength};
            boolean var9 = false;
            String var10000 = String.format(var13, Arrays.copyOf(var14, var14.length));
            Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.String.format(format, *args)");
//            headerLength = StringsKt.replace$default(var10000, ' ', '0', false, 4, (Object)null);
            String contentToHex = "";

            for(i = 0; i < content.length(); ++i) {
                if (content.charAt(i) <= '\t') {
                    contentToHex = contentToHex + '0';
                }

                contentToHex = contentToHex + Integer.toHexString(content.charAt(i));
            }

            String var15 = contentToHex;
            Charset var16 = Charsets.UTF_8;
            boolean var10 = false;
            if (var15 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                byte[] var17 = var15.getBytes(var16);
                Intrinsics.checkExpressionValueIsNotNull(var17, "(this as java.lang.String).getBytes(charset)");
                return var17;
            }
        }

        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

