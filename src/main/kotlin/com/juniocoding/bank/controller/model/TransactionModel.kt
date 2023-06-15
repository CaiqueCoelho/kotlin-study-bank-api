package com.juniocoding.bank.controller.model

import com.juniocoding.bank.controller.repository.TransactionDBModel

class TransactionModel(
    val targetAccount: String,
    val amount: Double,
    val description: String
    )

fun TransactionModel.convertToDBModel() = TransactionDBModel(
        accountIdentifier = this.targetAccount,
        amount = this.amount,
        description = this.description

)