package com.example.clubdeportivo_grupo5_comisionb

data class Pago(
    val id: Int = 0,
    val tipoCliente: String,
    val clienteId: Int,
    val nombreCliente: String,
    val dniCliente: String,
    val concepto: String,
    val monto: Double,
    val medioPago: String,
    val fechaPago: String,
    val fechaVencimientoGenerada: String
)