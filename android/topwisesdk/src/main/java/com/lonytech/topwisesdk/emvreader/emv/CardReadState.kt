package com.lonytech.topwisesdk.emvreader.emv

enum class CardReadState {
    Loading,
    CardData,
    CardReadTimeOut,
    CallBackError,
    CallBackCanceled,
    CallBackTransResult,
    CardDetected,
}




