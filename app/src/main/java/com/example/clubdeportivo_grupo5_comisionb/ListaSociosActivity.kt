package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListaSociosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_socios)

        val btnVolverListaSocios: Button = findViewById(R.id.btnVolverListaSocios)
        val txtMenuListaSocios: TextView = findViewById(R.id.txtMenuListaSocios)
        val btnPerfilGuillermo: Button = findViewById(R.id.btnPerfilGuillermo)
        val btnPerfilJulia: Button = findViewById(R.id.btnPerfilJulia)

        btnVolverListaSocios.setOnClickListener {
            finish()
        }

        txtMenuListaSocios.setOnClickListener {
            Toast.makeText(this, "Filtros avanzados disponibles en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnPerfilGuillermo.setOnClickListener {
            Toast.makeText(this, "Ficha de Guillermo disponible en próximas etapas", Toast.LENGTH_SHORT).show()
        }

        btnPerfilJulia.setOnClickListener {
            val intent = Intent(this, PerfilSocioActivity::class.java)
            startActivity(intent)
        }
    }
}