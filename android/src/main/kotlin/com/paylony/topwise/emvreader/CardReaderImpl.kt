package com.paylony.topwise.emvreader

import android.content.Context
import android.os.RemoteException
import com.paylony.topwise.emvreader.app.PosApplication
import com.paylony.topwise.emvreader.card.CardManager
import com.paylony.topwise.emvreader.card.CardReader
import com.paylony.topwise.emvreader.emv.CardReadChannel
import com.paylony.topwise.emvreader.emv.CardReadResult
import com.paylony.topwise.emvreader.emv.CardReadState
import com.paylony.topwise.emvreader.emv.Processor
import com.topwise.cloudpos.aidl.card.AidlCheckCardListener
import com.topwise.cloudpos.aidl.magcard.TrackData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CardReaderImpl(val context: Context) : CardReader {

    private val mCheckCard by lazy { DeviceTopUsdkServiceManager.instance?.getCheckCard() }
    override val searchCardTime: Int
        get() = 30000
    val job = Job()
    private val coroutineContext = job + Dispatchers.Main
    private val mainScope = MainScope()
    override fun closeCardReader() {
        cancelCheckCard()
        CardManager.instance.stopCardDealService(context)
    }

    private val cardConsumeEmitter = MutableSharedFlow<CardReadState>()
    private val cardSchemeConsumeEmitter = MutableSharedFlow<CardReadState>()
    override suspend fun readCard(amount: String): Flow<CardReadState> {
        CardManager.instance.stopCardDealService(context)
        PosApplication.getApp().mConsumeData?.amount = amount
        PosApplication.getApp().transactionType = PosApplication.CONSUME
        PosApplication.getApp().processor = Processor.INTERSWITCH
        read()
        return cardConsumeEmitter
    }

    override suspend fun getCardScheme(): Flow<CardReadState> {
        CardManager.instance.stopCardDealService(context)
        PosApplication.getApp().transactionType = PosApplication.CARD_SCHEME
        PosApplication.getApp().mConsumeData?.amount = "0"
        PosApplication.getApp().processor = Processor.INTERSWITCH
        getScheme()
        return cardSchemeConsumeEmitter
    }


    private suspend fun read() {
        cardConsumeEmitter.emit(CardReadState.Loading)
        CardManager.instance.initCardExceptionCallBack(object : CardManager.CardExceptionCallBack {
            override fun callBackTimeOut() {
                mainScope.launch {
                    cardConsumeEmitter.emit(CardReadState.CardReadTimeOut)
                }
            }

            override fun callBackError(errorCode: Int) {
                mainScope.launch {

                    cardConsumeEmitter.emit(CardReadState.CallBackError(errorCode))
                }
            }

            override fun callBackCanceled() {
                mainScope.launch {

                    cardConsumeEmitter.emit(CardReadState.CallBackCanceled)
                }
            }

            override fun callBackTransResult(result: Int) {

            }

            override fun finishPreActivity() {

            }
        })

        CardManager.instance.setCardFoundListener(object : CardManager.Card {
            override fun searching() {
                mainScope.launch {
                    cardSchemeConsumeEmitter.emit(CardReadState.Loading)
                }
            }

            override fun onCardDetected(pan: String) {
                mainScope.launch {

                    cardConsumeEmitter.emit(CardReadState.CardDetected("Mubby", ""))
                }
            }

            override fun onCardReadResult(cardReadResult: CardReadResult) {
                mainScope.launch {
                    cardConsumeEmitter.emit(
                        CardReadState.CardData(
                            CardReadResult(
                                issuerApplicationData = cardReadResult.issuerApplicationData,
                                applicationVersionNumber = cardReadResult.applicationVersionNumber,
                                transactionType = cardReadResult.transactionType,
                                amount = cardReadResult.amount,
                                amountAuthorized = cardReadResult.amountAuthorized,
                                cardSeqenceNumber = cardReadResult.cardSeqenceNumber,
                                unifiedPaymentIccData = cardReadResult.unifiedPaymentIccData,
                                deviceSerialNumber = cardReadResult.deviceSerialNumber,
                                iccDataString = cardReadResult.iccDataString,
                                authorizationResponseCode = cardReadResult.authorizationResponseCode,
                                nibssIccSubset = cardReadResult.nibssIccSubset,
                                applicationTransactionCounter = cardReadResult.applicationTransactionCounter,
                                terminalVerificationResults = cardReadResult.terminalVerificationResults,
                                expirationDate = cardReadResult.expirationDate,
                                applicationPrimaryAccountNumber = cardReadResult.applicationPrimaryAccountNumber,
                                applicationPANSequenceNumber = cardReadResult.applicationPANSequenceNumber,
                                transactionDate = cardReadResult.transactionDate,
                                cryptogramInformationData = cardReadResult.cryptogramInformationData,
                                dedicatedFileName = cardReadResult.dedicatedFileName,
                                transactionSequenceNumber = cardReadResult.transactionSequenceNumber,
                                transactionSequenceCounter = cardReadResult.transactionSequenceCounter,
                                cryptogram = cardReadResult.cryptogram,
                                track2Data = cardReadResult.track2Data,
                                cardholderVerificationMethod = cardReadResult.cardholderVerificationMethod,
                                applicationInterchangeProfile = cardReadResult.applicationInterchangeProfile,
                                pinBlockDUKPT = cardReadResult.pinBlockDUKPT,
                                pinBlockTrippleDES = cardReadResult.pinBlockTrippleDES,
                                cardScheme = cardReadResult.cardScheme,
                                applicationDiscretionaryData = cardReadResult.applicationDiscretionaryData,
                                unpredictableNumber = cardReadResult.unpredictableNumber,
                                interfaceDeviceSerialNumber = cardReadResult.interfaceDeviceSerialNumber,
                                encryptedPinBlock = cardReadResult.encryptedPinBlock,
                                terminalType = cardReadResult.terminalType,
                                cardHolderName = cardReadResult.cardHolderName,
                                originalDeviceSerial = cardReadResult.originalDeviceSerial,
                                transactionCurrencyCode = cardReadResult.transactionCurrencyCode,
                                terminalCountryCode = cardReadResult.terminalCountryCode,
                                cashBackAmount = cardReadResult.cashBackAmount,
                                terminalCapabilities = cardReadResult.terminalCapabilities,
                                plainPinKey = cardReadResult.plainPinKey,
                                originalPan = cardReadResult.originalPan,
                            )
                        )
                    )
                }
            }
        })
        CardManager.instance.startCardDealService(context)
    }

    private suspend fun getScheme() {
        cardSchemeConsumeEmitter.emit(CardReadState.Loading)
        CardManager.instance.initCardExceptionCallBack(object : CardManager.CardExceptionCallBack {
            override fun callBackTimeOut() {
                mainScope.launch {
                    cardSchemeConsumeEmitter.emit(CardReadState.CardReadTimeOut)
                }
            }

            override fun callBackError(errorCode: Int) {
                mainScope.launch {

                    cardSchemeConsumeEmitter.emit(CardReadState.CallBackError(errorCode))
                }
            }

            override fun callBackCanceled() {
                mainScope.launch {

                    cardSchemeConsumeEmitter.emit(CardReadState.CallBackCanceled)
                }
            }

            override fun callBackTransResult(result: Int) {

            }

            override fun finishPreActivity() {

            }
        })
        CardManager.instance.setCardFoundListener(object : CardManager.Card {
            override fun searching() {
                mainScope.launch {
                    cardSchemeConsumeEmitter.emit(CardReadState.Loading)
                }
            }

            override fun onCardDetected(pan: String) {
                mainScope.launch {

                    cardSchemeConsumeEmitter.emit(CardReadState.CardDetected("Mubby", ""))
                }
            }

            override fun onCardReadResult(cardReadResult: CardReadResult) {
                mainScope.launch {
                    cardSchemeConsumeEmitter.emit(
                        CardReadState.CardData(
                            CardReadResult(
                                issuerApplicationData = cardReadResult.issuerApplicationData,
                                applicationVersionNumber = cardReadResult.applicationVersionNumber,
                                transactionType = cardReadResult.transactionType,
                                amount = cardReadResult.amount,
                                amountAuthorized = cardReadResult.amountAuthorized,
                                cardSeqenceNumber = cardReadResult.cardSeqenceNumber,
                                unifiedPaymentIccData = cardReadResult.unifiedPaymentIccData,
                                deviceSerialNumber = cardReadResult.deviceSerialNumber,
                                iccDataString = cardReadResult.iccDataString,
                                authorizationResponseCode = cardReadResult.authorizationResponseCode,
                                nibssIccSubset = cardReadResult.nibssIccSubset,
                                applicationTransactionCounter = cardReadResult.applicationTransactionCounter,
                                terminalVerificationResults = cardReadResult.terminalVerificationResults,
                                expirationDate = cardReadResult.expirationDate,
                                applicationPrimaryAccountNumber = cardReadResult.applicationPrimaryAccountNumber,
                                applicationPANSequenceNumber = cardReadResult.applicationPANSequenceNumber,
                                transactionDate = cardReadResult.transactionDate,
                                cryptogramInformationData = cardReadResult.cryptogramInformationData,
                                dedicatedFileName = cardReadResult.dedicatedFileName,
                                transactionSequenceNumber = cardReadResult.transactionSequenceNumber,
                                transactionSequenceCounter = cardReadResult.transactionSequenceCounter,
                                cryptogram = cardReadResult.cryptogram,
                                track2Data = cardReadResult.track2Data,
                                cardholderVerificationMethod = cardReadResult.cardholderVerificationMethod,
                                applicationInterchangeProfile = cardReadResult.applicationInterchangeProfile,
                                pinBlockDUKPT = cardReadResult.pinBlockDUKPT,
                                pinBlockTrippleDES = cardReadResult.pinBlockTrippleDES,
                                cardScheme = cardReadResult.cardScheme,
                                applicationDiscretionaryData = cardReadResult.applicationDiscretionaryData,
                                unpredictableNumber = cardReadResult.unpredictableNumber,
                                interfaceDeviceSerialNumber = cardReadResult.interfaceDeviceSerialNumber,
                                encryptedPinBlock = cardReadResult.encryptedPinBlock,
                                terminalType = cardReadResult.terminalType,
                                cardHolderName = cardReadResult.cardHolderName,
                                originalDeviceSerial = cardReadResult.originalDeviceSerial,
                                transactionCurrencyCode = cardReadResult.transactionCurrencyCode,
                                terminalCountryCode = cardReadResult.terminalCountryCode,
                                cashBackAmount = cardReadResult.cashBackAmount,
                                terminalCapabilities = cardReadResult.terminalCapabilities,
                                plainPinKey = cardReadResult.plainPinKey,
                                originalPan = cardReadResult.originalPan,
                            )
                        )
                    )
                }
            }
        })
        CardManager.instance.setCardSchemeListener(object : CardManager.CardSchemeListener {
            override fun onCardScheme(cardScheme: String, maskedPan: String) {
                mainScope.launch {
                    cardSchemeConsumeEmitter.emit(CardReadState.CardDetected(cardScheme, maskedPan))
                }
            }

        })
        CardManager.instance.startCardDealService(context)
    }

    override suspend fun listen(): CardReadChannel {
        return suspendCoroutine {
            synchronized(this) {
                try {
                    mCheckCard!!.checkCard(true,
                        true,
                        true,
                        searchCardTime,
                        object : AidlCheckCardListener.Stub() {
                            override fun onFindMagCard(p0: TrackData?) {
                                it.resume(CardReadChannel.MAG_STRIPE)
                            }

                            override fun onSwipeCardFail() {
                                it.resume(CardReadChannel.CardFailure)
                            }

                            override fun onFindICCard() {
                                it.resume(CardReadChannel.CHIP)
                            }

                            override fun onFindRFCard() {
                                it.resume(CardReadChannel.RFC)

                            }

                            override fun onTimeout() {
                                it.resume(CardReadChannel.TIMEOUT)
                            }

                            override fun onCanceled() {
                                it.resume(CardReadChannel.CANCELLED)
                            }

                            override fun onError(p0: Int) {
                                it.resume(CardReadChannel.ERROR(p0))
                            }

                        })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun cancelCheckCard() {
        synchronized(this) {
            try {
                if (mCheckCard != null) {
                    mCheckCard!!.cancelCheckCard()
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun onCardRemoved(): CardReadResult {
        TODO("Not yet implemented")
    }
}