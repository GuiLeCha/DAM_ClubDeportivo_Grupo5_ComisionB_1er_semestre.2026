package com.example.clubdeportivo_grupo5_comisionb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CarnetSocioActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carnet_socio)

        dbHelper = SQLiteHelper(this)

        val btnImprimirCarnet: Button = findViewById(R.id.btnImprimirCarnet)
        val btnCompartirCarnet: Button = findViewById(R.id.btnCompartirCarnet)
        val btnVolverPerfilSocio: Button = findViewById(R.id.btnVolverPerfilSocio)

        val socio = obtenerSocioSeleccionado()

        if (socio == null) {
            Toast.makeText(this, "No se encontró el socio para emitir carnet", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        cargarDatosCarnet(socio)

        btnImprimirCarnet.setOnClickListener {
            Toast.makeText(this, "Impresión de carnet disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnCompartirCarnet.setOnClickListener {
            Toast.makeText(this, "Compartir carnet disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnVolverPerfilSocio.setOnClickListener {
            finish()
        }
    }

    private fun obtenerSocioSeleccionado(): Socio? {
        val socioId = intent.getIntExtra("socioId", 0)

        if (socioId <= 0) {
            return null
        }

        return dbHelper.obtenerSocioPorId(socioId)
    }

    private fun cargarDatosCarnet(socio: Socio) {
        val txtInicialesCarnet: TextView = findViewById(R.id.txtInicialesCarnet)
        val txtNombreSocioCarnet: TextView = findViewById(R.id.txtNombreSocioCarnet)
        val txtNumeroSocioCarnet: TextView = findViewById(R.id.txtNumeroSocioCarnet)
        val txtEstadoCarnet: TextView = findViewById(R.id.txtEstadoCarnet)
        val txtVencimientoCarnet: TextView = findViewById(R.id.txtVencimientoCarnet)
        val txtCodigoCarnet: TextView = findViewById(R.id.txtCodigoCarnet)

        txtInicialesCarnet.text = obtenerIniciales(socio.nombre, socio.apellido)
        txtNombreSocioCarnet.text = socio.nombreCompleto
        txtNumeroSocioCarnet.text = "N° de socio: ${socio.id.toString().padStart(5, '0')}"

        txtEstadoCarnet.text = if (socio.carnetEntregado) {
            "Estado: Carnet entregado"
        } else {
            "Estado: Carnet pendiente de entrega"
        }

        txtVencimientoCarnet.text = "Vencimiento cuota: ${socio.fechaVencimientoCuota}"
        txtCodigoCarnet.text = "Código: SOC-${socio.id.toString().padStart(5, '0')}"
    }

    private fun obtenerIniciales(nombre: String, apellido: String): String {
        val inicialNombre = nombre.firstOrNull()?.uppercaseChar() ?: 'S'
        val inicialApellido = apellido.firstOrNull()?.uppercaseChar() ?: 'C'
        return "$inicialNombre$inicialApellido"
    }
}