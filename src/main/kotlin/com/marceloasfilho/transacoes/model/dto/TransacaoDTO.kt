package com.marceloasfilho.transacoes.model.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.OffsetDateTime

data class TransacaoDTO(
    @field:NotNull(message = "O valor deve ser preenchido")
    @field:DecimalMin(
        value = "0.0",
        inclusive = true,
        message = "A transação DEVE ter valor igual ou maior que 0 (zero)"
    )
    val valor: Double?,

    @field:NotNull(message = "Data e hora deve ser preenchido")
    @field:PastOrPresent(message = "A transação NÃO DEVE acontecer no futuro")
    val dataHora: OffsetDateTime?
)