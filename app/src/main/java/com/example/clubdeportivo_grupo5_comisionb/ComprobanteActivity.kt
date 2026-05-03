package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ComprobanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprobante)

        val btnDescargarComprobante: Button = findViewById(R.id.btnDescargarComprobante)
        val btnVolverMenuComprobante: Button = findViewById(R.id.btnVolverMenuComprobante)

        btnDescargarComprobante.setOnClickListener {
            Toast.makeText(this, "Descarga prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnVolverMenuComprobante.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}