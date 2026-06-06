package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilVisitanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_visitante)

        val btnVolverPerfilVisitante: Button = findViewById(R.id.btnVolverPerfilVisitante)
        val btnCobrarVisitante: Button = findViewById(R.id.btnCobrarVisitante)
        val btnEditarVisitante: Button = findViewById(R.id.btnEditarVisitante)

        btnVolverPerfilVisitante.setOnClickListener {
            finish()
        }

        btnCobrarVisitante.setOnClickListener {
            val intent = Intent(this, RegistrarPagoActivity::class.java)
            startActivity(intent)
        }

        btnEditarVisitante.setOnClickListener {
            Toast.makeText(this, "Edición de visitante disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }
    }
}