package com.topwise.library.util.emv;

import kotlin.Metadata;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"},
        d2 = {"Lcom/topwise/library/util/emv/AccountType;", "", "(Ljava/lang/String;I)V", "DEFAULT", "SAVINGS", "CURRENT", "CREDIT", "UNIVERSAL", "INVESTMENT", "app_release"}
)
public enum AccountType {
    DEFAULT,
    SAVINGS,
    CURRENT,
    CREDIT,
    UNIVERSAL,
    INVESTMENT;
}
