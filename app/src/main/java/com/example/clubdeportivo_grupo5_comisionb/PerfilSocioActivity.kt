package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_socio)

        val btnVolverPerfilSocioSuperior: Button = findViewById(R.id.btnVolverPerfilSocioSuperior)
        val btnCobrarSocio: Button = findViewById(R.id.btnCobrarSocio)
        val btnEditarSocio: Button = findViewById(R.id.btnEditarSocio)
        val btnEmitirCarnet: Button = findViewById(R.id.btnEmitirCarnet)

        btnVolverPerfilSocioSuperior.setOnClickListener {
            finish()
        }

        btnCobrarSocio.setOnClickListener {
            val intent = Intent(this, RegistrarPagoActivity::class.java)
            startActivity(intent)
        }

        btnEditarSocio.setOnClickListener {
            Toast.makeText(this, "Edición de socio disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnEmitirCarnet.setOnClickListener {
            val intent = Intent(this, CarnetSocioActivity::class.java)
            startActivity(intent)
        }
    }
}