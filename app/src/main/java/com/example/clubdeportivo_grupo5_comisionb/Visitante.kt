package com.example.clubdeportivo_grupo5_comisionb

data class Visitante(
    val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val dni: String,
    val telefono: String,
    val email: String,
    val actividad: String,
    val aptoFisico: Boolean,
    val fechaRegistro: String,
    val activo: Boolean = true
) {
    val nombreCompleto: String
        get() = "$nombre $apellido"
}