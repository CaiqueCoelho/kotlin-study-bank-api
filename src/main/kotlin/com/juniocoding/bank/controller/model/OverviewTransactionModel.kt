package com.juniocoding.bank.controller.model

import com.juniocoding.bank.controller.repository.TransactionDBModel
import java.util.*

class OverviewTransactionModel(
        val targetAccount: String,
        val amount: Double,
        val description: String,
        val date: Date,
        val id: String
)

fun TransactionDBModel.convertToOverViewTransactionModel() = OverviewTransactionModel(
        targetAccount = this.accountIdentifier,
        amount = this.amount,
        description = this.description,
        date = this.date,
        id = this.id.toString()

)