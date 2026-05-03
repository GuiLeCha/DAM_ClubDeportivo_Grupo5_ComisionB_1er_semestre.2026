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
            val intent = Intent(this, ComprobanteActivity::class.java)
            startActivity(intent)
        }

        btnVolverMenuPago.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}