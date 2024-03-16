package com.paylony.topwise.emvreader.emv

sealed class CardReadState {
    data class CardData(val cardReadResult: CardReadResult) : CardReadState()
    object CardReadTimeOut : CardReadState()
    data class CallBackError(val errorCode: Int) : CardReadState()
    object CallBackCanceled : CardReadState()
    object CallBackTransResult : CardReadState()
    data class CardDetected(val cardMaskedPan: String, val cardHolderName: String) : CardReadState()
    object Loading : CardReadState()
}





