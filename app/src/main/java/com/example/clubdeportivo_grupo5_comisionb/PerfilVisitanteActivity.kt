package com.example.clubdeportivo_grupo5_comisionb

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilVisitanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_visitante)

        val btnCobrarVisitante: Button = findViewById(R.id.btnCobrarVisitante)
        val btnEditarVisitante: Button = findViewById(R.id.btnEditarVisitante)

        btnCobrarVisitante.setOnClickListener {
            Toast.makeText(this, "Cobro disponible en el módulo de cobros", Toast.LENGTH_SHORT).show()
        }

        btnEditarVisitante.setOnClickListener {
            Toast.makeText(this, "Edición prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }
    }
}