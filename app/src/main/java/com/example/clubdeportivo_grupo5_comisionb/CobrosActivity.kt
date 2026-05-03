package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CobrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cobros)

        val btnCobrarGuillermo: Button = findViewById(R.id.btnCobrarGuillermo)
        val btnCobrarJulia: Button = findViewById(R.id.btnCobrarJulia)
        val btnCobrarFernando: Button = findViewById(R.id.btnCobrarFernando)

        btnCobrarGuillermo.setOnClickListener {
            Toast.makeText(this, "Cobro de Guillermo previsto para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnCobrarJulia.setOnClickListener {
            val intent = Intent(this, RegistrarPagoActivity::class.java)
            startActivity(intent)
        }

        btnCobrarFernando.setOnClickListener {
            Toast.makeText(this, "Cobro de Fernando previsto para próxima etapa", Toast.LENGTH_SHORT).show()
        }
    }
}