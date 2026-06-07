package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PagoExitosoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_exitoso)

        val btnVerComprobante: Button = findViewById(R.id.btnVerComprobante)
        val btnVolverMenuPago: Button = findViewById(R.id.btnVolverMenuPago)

        btnVerComprobante.setOnClickListener {
            val intentComprobante = Intent(this, ComprobanteActivity::class.java)
            copiarExtras(intentComprobante)
            startActivity(intentComprobante)
        }

        btnVolverMenuPago.setOnClickListener {
            val intentMenu = Intent(this, MenuActivity::class.java)
            intentMenu.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intentMenu)
            finish()
        }
    }

    private fun copiarExtras(intentDestino: Intent) {
        intentDestino.putExtra("pagoId", intent.getIntExtra("pagoId", 0))
        intentDestino.putExtra("tipoCliente", intent.getStringExtra("tipoCliente") ?: "")
        intentDestino.putExtra("clienteId", intent.getIntExtra("clienteId", 0))
        intentDestino.putExtra("nombreCliente", intent.getStringExtra("nombreCliente") ?: "")
        intentDestino.putExtra("dniCliente", intent.getStringExtra("dniCliente") ?: "")
        intentDestino.putExtra("concepto", intent.getStringExtra("concepto") ?: "")
        intentDestino.putExtra("monto", intent.getDoubleExtra("monto", 0.0))
        intentDestino.putExtra("medioPago", intent.getStringExtra("medioPago") ?: "")
        intentDestino.putExtra("fechaPago", intent.getStringExtra("fechaPago") ?: "")
        intentDestino.putExtra("fechaVencimiento", intent.getStringExtra("fechaVencimiento") ?: "")
    }
}