package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilVisitanteActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private var visitanteSeleccionado: Visitante? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_visitante)

        dbHelper = SQLiteHelper(this)

        val btnVolverPerfilVisitante: Button = findViewById(R.id.btnVolverPerfilVisitante)
        val btnCobrarVisitante: Button = findViewById(R.id.btnCobrarVisitante)
        val btnEditarVisitante: Button = findViewById(R.id.btnEditarVisitante)

        btnVolverPerfilVisitante.setOnClickListener {
            finish()
        }

        visitanteSeleccionado = obtenerVisitanteSeleccionado()

        if (visitanteSeleccionado == null) {
            Toast.makeText(this, "No se encontró el visitante seleccionado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        cargarDatosVisitante(visitanteSeleccionado!!)

        btnCobrarVisitante.setOnClickListener {
            val visitante = visitanteSeleccionado ?: return@setOnClickListener

            val intent = Intent(this, RegistrarPagoActivity::class.java)
            intent.putExtra("tipoCliente", "VISITANTE")
            intent.putExtra("clienteId", visitante.id)
            intent.putExtra("nombreCliente", visitante.nombreCompleto)
            intent.putExtra("dniCliente", visitante.dni)
            intent.putExtra("concepto", "Actividad diaria")
            intent.putExtra("monto", 3500.0)
            intent.putExtra("actividad", visitante.actividad)
            startActivity(intent)
        }

        btnEditarVisitante.setOnClickListener {
            Toast.makeText(this, "Edición de visitante disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val visitanteActual = obtenerVisitanteSeleccionado()
        if (visitanteActual != null) {
            visitanteSeleccionado = visitanteActual
            cargarDatosVisitante(visitanteActual)
        }
    }

    private fun obtenerVisitanteSeleccionado(): Visitante? {
        val visitanteId = intent.getIntExtra("visitanteId", 0)

        if (visitanteId <= 0) {
            return null
        }

        return dbHelper.obtenerVisitantePorId(visitanteId)
    }

    private fun cargarDatosVisitante(visitante: Visitante) {
        val txtInicialesPerfilVisitante: TextView = findViewById(R.id.txtInicialesPerfilVisitante)
        val txtNombrePerfilVisitante: TextView = findViewById(R.id.txtNombrePerfilVisitante)
        val txtTipoPerfilVisitante: TextView = findViewById(R.id.txtTipoPerfilVisitante)
        val txtActividadPerfilVisitante: TextView = findViewById(R.id.txtActividadPerfilVisitante)
        val txtAptoPerfilVisitante: TextView = findViewById(R.id.txtAptoPerfilVisitante)
        val txtDniPerfilVisitante: TextView = findViewById(R.id.txtDniPerfilVisitante)
        val txtEmailPerfilVisitante: TextView = findViewById(R.id.txtEmailPerfilVisitante)
        val txtTelefonoPerfilVisitante: TextView = findViewById(R.id.txtTelefonoPerfilVisitante)

        txtInicialesPerfilVisitante.text = obtenerIniciales(visitante.nombre, visitante.apellido)
        txtNombrePerfilVisitante.text = visitante.nombreCompleto
        txtTipoPerfilVisitante.text = "Visitante N° ${visitante.id.toString().padStart(5, '0')}"
        txtActividadPerfilVisitante.text = "Actividad: ${visitante.actividad}"
        txtAptoPerfilVisitante.text = "Apto físico: ${if (visitante.aptoFisico) "Sí" else "No"} - Fecha registro: ${visitante.fechaRegistro}"
        txtDniPerfilVisitante.text = "DNI: ${visitante.dni}"
        txtEmailPerfilVisitante.text = "Email: ${obtenerTextoOAlternativo(visitante.email, "Sin email")}"
        txtTelefonoPerfilVisitante.text = "Teléfono: ${obtenerTextoOAlternativo(visitante.telefono, "Sin teléfono")}"
    }

    private fun obtenerTextoOAlternativo(texto: String, alternativo: String): String {
        return if (texto.isNotEmpty()) texto else alternativo
    }

    private fun obtenerIniciales(nombre: String, apellido: String): String {
        val inicialNombre = nombre.firstOrNull()?.uppercaseChar() ?: 'V'
        val inicialApellido = apellido.firstOrNull()?.uppercaseChar() ?: 'C'
        return "$inicialNombre$inicialApellido"
    }
}