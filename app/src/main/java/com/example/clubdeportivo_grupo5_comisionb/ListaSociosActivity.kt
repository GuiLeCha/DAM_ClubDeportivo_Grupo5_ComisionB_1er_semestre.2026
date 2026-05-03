package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListaSociosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_socios)

        val btnPerfilGuillermo: Button = findViewById(R.id.btnPerfilGuillermo)
        val btnPerfilJulia: Button = findViewById(R.id.btnPerfilJulia)

        btnPerfilGuillermo.setOnClickListener {
            Toast.makeText(this, "Ficha de Guillermo prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnPerfilJulia.setOnClickListener {
            val intent = Intent(this, PerfilSocioActivity::class.java)
            startActivity(intent)
        }
    }
}