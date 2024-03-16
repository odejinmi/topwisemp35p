package com.paylony.model

data class WithdrawErrorBody(
    val status : Int?,
    val data: Any?,
    val message : String?,
    val error : String?
)