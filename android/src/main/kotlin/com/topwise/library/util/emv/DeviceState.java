package com.topwise.library.util.emv;

import androidx.annotation.Keep;
import kotlin.Metadata;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0014\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0015"},
        d2 = {"Lcom/topwise/library/util/emv/DeviceState;", "", "(Ljava/lang/String;I)V", "INSERT_CARD", "PROCESSING", "INPUT_PIN", "PIN_DATA", "INFO", "REMOVE_CARD", "READCARD_FAILED", "FAILED", "DECLINED", "APPROVED", "AWAITING_ONLINE_RESPONSE", "BLUETOOTH_CONNECTED", "BLUETOOTH_FAILED", "PRINTER_BIND_SERVICE", "PRINTER_SERVICE_CONNECTION", "PRINTER_SUCCESS", "PRINTER_ERROR", "DEVICE_INFO", "app_release"}
)
@Keep
public enum DeviceState {
    INSERT_CARD,
    PROCESSING,
    INPUT_PIN,
    PIN_DATA,
    INFO,
    REMOVE_CARD,
    READCARD_FAILED,
    FAILED,
    DECLINED,
    APPROVED,
    AWAITING_ONLINE_RESPONSE,
    BLUETOOTH_CONNECTED,
    BLUETOOTH_FAILED,
    PRINTER_BIND_SERVICE,
    PRINTER_SERVICE_CONNECTION,
    PRINTER_SUCCESS,
    PRINTER_ERROR,
    DEVICE_INFO;
}
