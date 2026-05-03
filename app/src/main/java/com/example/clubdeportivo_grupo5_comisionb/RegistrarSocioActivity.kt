package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegistrarSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_socio)

        val btnAgregarSocio: Button = findViewById(R.id.btnAgregarSocio)

        btnAgregarSocio.setOnClickListener {
            val intent = Intent(this, RegistroExitosoActivity::class.java)
            startActivity(intent)
        }
    }
}