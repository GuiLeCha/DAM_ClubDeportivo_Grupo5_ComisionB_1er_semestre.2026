package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CobrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cobros)

        val btnVolverCobros: Button = findViewById(R.id.btnVolverCobros)
        val txtMenuCobros: TextView = findViewById(R.id.txtMenuCobros)
        val btnCobrarGuillermo: Button = findViewById(R.id.btnCobrarGuillermo)
        val btnCobrarJulia: Button = findViewById(R.id.btnCobrarJulia)
        val btnCobrarFernando: Button = findViewById(R.id.btnCobrarFernando)

        btnVolverCobros.setOnClickListener {
            finish()
        }

        txtMenuCobros.setOnClickListener {
            Toast.makeText(this, "Menú de opciones disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnCobrarGuillermo.setOnClickListener {
            Toast.makeText(this, "Cobro de Guillermo disponible en próximas etapas", Toast.LENGTH_SHORT).show()
        }

        btnCobrarJulia.setOnClickListener {
            val intent = Intent(this, RegistrarPagoActivity::class.java)
            startActivity(intent)
        }

        btnCobrarFernando.setOnClickListener {
            Toast.makeText(this, "Cobro de Fernando disponible en próximas etapas", Toast.LENGTH_SHORT).show()
        }
    }
}