package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilSocioActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var btnEmitirCarnet: Button

    private var socioSeleccionado: Socio? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_socio)

        dbHelper = SQLiteHelper(this)

        val btnVolverPerfilSocioSuperior: Button = findViewById(R.id.btnVolverPerfilSocioSuperior)
        val btnCobrarSocio: Button = findViewById(R.id.btnCobrarSocio)
        val btnEditarSocio: Button = findViewById(R.id.btnEditarSocio)
        btnEmitirCarnet = findViewById(R.id.btnEmitirCarnet)

        btnVolverPerfilSocioSuperior.setOnClickListener {
            finish()
        }

        socioSeleccionado = obtenerSocioSeleccionado()

        if (socioSeleccionado == null) {
            Toast.makeText(this, "No se encontró el socio seleccionado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        cargarDatosSocio(socioSeleccionado!!)

        btnCobrarSocio.setOnClickListener {
            val socio = socioSeleccionado ?: return@setOnClickListener

            val intent = Intent(this, RegistrarPagoActivity::class.java)
            intent.putExtra("tipoCliente", "SOCIO")
            intent.putExtra("clienteId", socio.id)
            intent.putExtra("nombreCliente", socio.nombreCompleto)
            intent.putExtra("dniCliente", socio.dni)
            intent.putExtra("concepto", "Cuota mensual")
            intent.putExtra("monto", 12000.0)
            startActivity(intent)
        }

        btnEditarSocio.setOnClickListener {
            Toast.makeText(this, "Edición de socio disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnEmitirCarnet.setOnClickListener {
            emitirOVerCarnet()
        }
    }

    override fun onResume() {
        super.onResume()

        val socioActual = obtenerSocioSeleccionado()
        if (socioActual != null) {
            socioSeleccionado = socioActual
            cargarDatosSocio(socioActual)
        }
    }

    private fun obtenerSocioSeleccionado(): Socio? {
        val socioId = intent.getIntExtra("socioId", 0)

        if (socioId <= 0) {
            return null
        }

        return dbHelper.obtenerSocioPorId(socioId)
    }

    private fun cargarDatosSocio(socio: Socio) {
        val txtInicialesPerfilSocio: TextView = findViewById(R.id.txtInicialesPerfilSocio)
        val txtNombrePerfilSocio: TextView = findViewById(R.id.txtNombrePerfilSocio)
        val txtNumeroPerfilSocio: TextView = findViewById(R.id.txtNumeroPerfilSocio)
        val txtCuotaPerfilSocio: TextView = findViewById(R.id.txtCuotaPerfilSocio)
        val txtEstadoPerfilSocio: TextView = findViewById(R.id.txtEstadoPerfilSocio)
        val txtDniPerfilSocio: TextView = findViewById(R.id.txtDniPerfilSocio)
        val txtEmailPerfilSocio: TextView = findViewById(R.id.txtEmailPerfilSocio)
        val txtTelefonoPerfilSocio: TextView = findViewById(R.id.txtTelefonoPerfilSocio)
        val txtDireccionPerfilSocio: TextView = findViewById(R.id.txtDireccionPerfilSocio)
        val txtAptoPerfilSocio: TextView = findViewById(R.id.txtAptoPerfilSocio)

        txtInicialesPerfilSocio.text = obtenerIniciales(socio.nombre, socio.apellido)
        txtNombrePerfilSocio.text = socio.nombreCompleto
        txtNumeroPerfilSocio.text = "N° de socio: ${socio.id.toString().padStart(5, '0')}"
        txtCuotaPerfilSocio.text = "Vencimiento cuota: ${socio.fechaVencimientoCuota}"

        val estadoCuota = obtenerEstadoCuota(socio.fechaVencimientoCuota)

        txtEstadoPerfilSocio.text = "Estado: $estadoCuota"

        if (estadoCuota == "En mora") {
            txtCuotaPerfilSocio.setTextColor(getColor(R.color.rojo_alerta))
            txtEstadoPerfilSocio.setTextColor(getColor(R.color.rojo_alerta))
        } else {
            txtCuotaPerfilSocio.setTextColor(getColor(R.color.verde_ok))
            txtEstadoPerfilSocio.setTextColor(getColor(R.color.verde_ok))
        }

        txtDniPerfilSocio.text = "DNI: ${socio.dni}"
        txtEmailPerfilSocio.text = "Email: ${obtenerTextoOAlternativo(socio.email, "Sin email")}"
        txtTelefonoPerfilSocio.text = "Teléfono: ${obtenerTextoOAlternativo(socio.telefono, "Sin teléfono")}"
        txtDireccionPerfilSocio.text = "Dirección: ${obtenerTextoOAlternativo(socio.direccion, "Sin dirección")}"
        txtAptoPerfilSocio.text = "Apto físico: ${if (socio.aptoFisico) "Sí" else "No"} - Carnet: ${if (socio.carnetEntregado) "Entregado" else "Pendiente"}"

        btnEmitirCarnet.text = if (socio.carnetEntregado) {
            "Ver Carnet"
        } else {
            "Emitir Carnet"
        }
    }

    private fun emitirOVerCarnet() {
        val socio = socioSeleccionado ?: return

        if (socio.carnetEntregado) {
            abrirCarnet(socio)
            return
        }

        val resultado = dbHelper.actualizarVencimientoSocioPorId(
            socio.id,
            socio.fechaVencimientoCuota,
            true
        )

        if (resultado > 0) {
            val socioActualizado = dbHelper.obtenerSocioPorId(socio.id)

            if (socioActualizado != null) {
                socioSeleccionado = socioActualizado
                cargarDatosSocio(socioActualizado)
                Toast.makeText(this, "Carnet emitido correctamente", Toast.LENGTH_SHORT).show()
                abrirCarnet(socioActualizado)
            } else {
                Toast.makeText(this, "Carnet emitido correctamente", Toast.LENGTH_SHORT).show()
                abrirCarnet(socio)
            }
        } else {
            Toast.makeText(this, "No se pudo emitir el carnet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirCarnet(socio: Socio) {
        val intent = Intent(this, CarnetSocioActivity::class.java)
        intent.putExtra("socioId", socio.id)
        startActivity(intent)
    }

    private fun obtenerEstadoCuota(fechaVencimiento: String): String {
        val hoy = dbHelper.obtenerFechaHoy()

        return when {
            fechaVencimiento < hoy -> "En mora"
            fechaVencimiento == hoy -> "Vence hoy"
            else -> "Vigente"
        }
    }

    private fun obtenerTextoOAlternativo(texto: String, alternativo: String): String {
        return if (texto.isNotEmpty()) texto else alternativo
    }

    private fun obtenerIniciales(nombre: String, apellido: String): String {
        val inicialNombre = nombre.firstOrNull()?.uppercaseChar() ?: 'S'
        val inicialApellido = apellido.firstOrNull()?.uppercaseChar() ?: 'C'
        return "$inicialNombre$inicialApellido"
    }
}