package com.paylony.topwise.emvreader.card

import com.paylony.topwise.emvreader.emv.CardReadChannel
import com.paylony.topwise.emvreader.emv.CardReadResult
import com.paylony.topwise.emvreader.emv.CardReadState
import kotlinx.coroutines.flow.Flow

interface CardReader {
    val searchCardTime: Int
    fun closeCardReader()
    suspend fun readCard(amount: String): Flow<CardReadState>
    suspend fun getCardScheme(): Flow<CardReadState>
    suspend fun listen(): CardReadChannel
    suspend fun onCardRemoved(): CardReadResult
}