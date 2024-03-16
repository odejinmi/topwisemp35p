package com.topwise.library.model;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"},
        d2 = {"Lcom/topwise/library/model/CardAccountType;", "", "code", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getCode", "()Ljava/lang/String;", "DEFAULT", "SAVINGS", "CURRENT", "CREDIT", "app_release"}
)
public enum CardAccountType {
    DEFAULT("default"),
    SAVINGS("savings"),
    CURRENT("current"),
    CREDIT("credit");

    @NotNull
    private final String code;

    @NotNull
    public final String getCode() {
        return this.code;
    }

    private CardAccountType(String code) {
        this.code = code;
    }
}
