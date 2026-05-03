package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegistrarVisitanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_visitante)

        val btnAgregarVisitante: Button = findViewById(R.id.btnAgregarVisitante)

        btnAgregarVisitante.setOnClickListener {
            val intent = Intent(this, RegistroExitosoActivity::class.java)
            startActivity(intent)
        }
    }
}