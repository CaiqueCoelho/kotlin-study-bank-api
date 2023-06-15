package com.juniocoding.bank

import com.fasterxml.jackson.databind.ObjectMapper
import com.juniocoding.bank.controller.model.TransactionModel
import com.juniocoding.bank.controller.repository.TransactionDBModel
import com.juniocoding.bank.controller.repository.TransferRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest
class PaymentControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var paymentsRepository: TransferRepository

    @Test
    fun `should submit transaction with success`() {
        val transfer = TransactionModel(
                amount = 1.50,
                description = "store 1",
                targetAccount = "18271827"
        )

        every { paymentsRepository.save(any())} returns mockk()

        mockMvc.perform(post("/transfer/new")
            .content(ObjectMapper().writeValueAsString(transfer))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun `should get transaction with success`() {
        val mockTransaction = mockk<TransactionDBModel>().apply {
            every { amount } returns 1.50
            every { description } returns "store 1"
            every { accountIdentifier } returns "NL47INGB8845464385"
            every { date } returns Date()
            every { id } returns UUID.randomUUID()
        }

        every { paymentsRepository.findAll() } returns listOf(mockTransaction)

        mockMvc.perform(get("/transfer/all").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].description").value("store 1"))
    }

    @Test
    fun `should return a bad request error if the request there's no body`() {
        mockMvc.perform(post("/transfer/new")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
    }
}