package com.marceloasfilho.transacoes.model.dto

data class EstatisticaDTO(
    val count: Int = 0,
    val sum: Double = 0.00,
    val avg: Double = 0.00,
    val min: Double = 0.00,
    val max: Double = 0.00
)