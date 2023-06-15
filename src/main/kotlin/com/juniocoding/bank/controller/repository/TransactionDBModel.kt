package com.juniocoding.bank.controller.repository

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="TransactionDBModel")
class TransactionDBModel(
        val accountIdentifier: String,
        val amount: Double,
        val description: String = "",
) {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: UUID? = null
        private set

    val date: Date = Date()
}