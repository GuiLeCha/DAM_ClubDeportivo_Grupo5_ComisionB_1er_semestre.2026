package com.example.clubdeportivo_grupo5_comisionb

data class Socio(
    val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val dni: String,
    val telefono: String,
    val email: String,
    val direccion: String,
    val fechaNacimiento: String,
    val aptoFisico: Boolean,
    val carnetEntregado: Boolean,
    val fechaAlta: String,
    val fechaVencimientoCuota: String,
    val activo: Boolean = true
) {
    val nombreCompleto: String
        get() = "$nombre $apellido"
}