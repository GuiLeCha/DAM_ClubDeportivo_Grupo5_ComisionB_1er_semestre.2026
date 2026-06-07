package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ComprobanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprobante)

        val txtComprobanteNumero: TextView = findViewById(R.id.txtComprobanteNumero)
        val txtClienteComprobante: TextView = findViewById(R.id.txtClienteComprobante)
        val txtDetalleComprobante: TextView = findViewById(R.id.txtDetalleComprobante)
        val txtConceptoComprobante: TextView = findViewById(R.id.txtConceptoComprobante)
        val txtFechaComprobante: TextView = findViewById(R.id.txtFechaComprobante)
        val txtMedioComprobante: TextView = findViewById(R.id.txtMedioComprobante)
        val txtTotalComprobante: TextView = findViewById(R.id.txtTotalComprobante)

        val btnDescargarComprobante: Button = findViewById(R.id.btnDescargarComprobante)
        val btnVolverMenuComprobante: Button = findViewById(R.id.btnVolverMenuComprobante)

        val pagoId = intent.getIntExtra("pagoId", 0)
        val tipoCliente = intent.getStringExtra("tipoCliente") ?: ""
        val clienteId = intent.getIntExtra("clienteId", 0)
        val nombreCliente = intent.getStringExtra("nombreCliente") ?: "Cliente"
        val dniCliente = intent.getStringExtra("dniCliente") ?: ""
        val concepto = intent.getStringExtra("concepto") ?: "Pago"
        val monto = intent.getDoubleExtra("monto", 0.0)
        val medioPago = intent.getStringExtra("medioPago") ?: "Sin informar"
        val fechaPago = intent.getStringExtra("fechaPago") ?: ""
        val fechaVencimiento = intent.getStringExtra("fechaVencimiento") ?: ""

        txtComprobanteNumero.text = "Comprobante N° ${pagoId.toString().padStart(5, '0')}"
        txtClienteComprobante.text = "Cliente: $nombreCliente"
        txtDetalleComprobante.text = "$tipoCliente N° $clienteId - DNI: $dniCliente"
        txtConceptoComprobante.text = "Concepto: $concepto"
        txtFechaComprobante.text = if (fechaVencimiento.isNotEmpty()) {
            "Fecha pago: $fechaPago - Vence: $fechaVencimiento"
        } else {
            "Fecha pago: $fechaPago"
        }
        txtMedioComprobante.text = "Medio de pago: $medioPago"
        txtTotalComprobante.text = "TOTAL: $ ${monto.toInt()}"

        btnDescargarComprobante.setOnClickListener {
            Toast.makeText(this, "Descarga prevista para futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnVolverMenuComprobante.setOnClickListener {
            val intentMenu = Intent(this, MenuActivity::class.java)
            intentMenu.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intentMenu)
            finish()
        }
    }
}