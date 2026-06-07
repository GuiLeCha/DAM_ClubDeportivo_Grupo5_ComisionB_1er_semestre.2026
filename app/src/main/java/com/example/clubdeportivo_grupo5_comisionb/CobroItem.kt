package com.example.clubdeportivo_grupo5_comisionb

data class CobroItem(
    val tipoCliente: String,
    val clienteId: Int,
    val nombreCliente: String,
    val dniCliente: String,
    val concepto: String,
    val monto: Double,
    val detalle: String
)