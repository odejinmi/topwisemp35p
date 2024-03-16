package com.topwise.library.model;

import com.topwise.library.util.TerminalUtils;
import com.topwise.library.util.iso.ISOUtilKt;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jpos.iso.ISOMsg;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0005¨\u0006\u0006"},
        d2 = {"toIso", "Lorg/jpos/iso/ISOMsg;", "Lcom/topwise/library/model/NetworkManagementRequestDto;", "toNetworkManagementResponse", "Lcom/topwise/library/model/NetworkManagementResponse;", "", "app_release"}
)
public final class NetworkReqResKt {
    @NotNull
    public static final NetworkManagementResponse toNetworkManagementResponse(@NotNull String $this$toNetworkManagementResponse) {
        Intrinsics.checkParameterIsNotNull($this$toNetworkManagementResponse, "$this$toNetworkManagementResponse");
//        List array = StringsKt.split$default((CharSequence)$this$toNetworkManagementResponse, new String[]{"|"}, false, 0, 6, (Object)null);
        String[] parts = $this$toNetworkManagementResponse.split("/|");
        List array = Arrays.asList(parts);
        String var10002 = (String)array.get(1);
        String var10003 = (String)array.get(2);
        String var10004 = (String)array.get(3);
        Object var2 = null;
        Object var3 = null;
        String var4 = var10004;
        String var5 = var10003;
        return new NetworkManagementResponse(var10002, var4, var5, (String)var3, (String)var2, 24, (DefaultConstructorMarker)null);
    }

    @NotNull
    public static final ISOMsg toIso(@NotNull NetworkManagementRequestDto $this$toIso) {
        Intrinsics.checkParameterIsNotNull($this$toIso, "$this$toIso");
        String tag = "01";
        String var10000 = $this$toIso.getSerialNumber();
        String length = ISOUtilKt.leftPad(String.valueOf(var10000 != null ? var10000.length() : null), 3, '0');
        (new StringBuilder()).append(tag).append(length).append($this$toIso.getSerialNumber()).toString();
        ISOMsg iso = new ISOMsg();
        TerminalUtils.Companion.logISOMsg(iso);
        return iso;
    }
}
