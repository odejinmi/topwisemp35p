package com.lonytech.topwisesdk.emvreader.emv

sealed class CardReadChannel {
    object MAG_STRIPE : CardReadChannel()
    object CHIP : CardReadChannel()

    object RFC : CardReadChannel()

    object TIMEOUT : CardReadChannel()
    object CANCELLED : CardReadChannel()
    object CardFailure : CardReadChannel()
    object SWIPE_CARD_FAILED : CardReadChannel()
    data class ERROR(val code: Int) : CardReadChannel()

}