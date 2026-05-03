package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegistrarPagoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_pago)

        val btnRegistrarPago: Button = findViewById(R.id.btnRegistrarPago)

        btnRegistrarPago.setOnClickListener {
            val intent = Intent(this, PagoExitosoActivity::class.java)
            startActivity(intent)
        }
    }
}