package com.example.clubdeportivo_grupo5_comisionb

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CarnetSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carnet_socio)

        val btnImprimirCarnet: Button = findViewById(R.id.btnImprimirCarnet)
        val btnCompartirCarnet: Button = findViewById(R.id.btnCompartirCarnet)
        val btnVolverPerfilSocio: Button = findViewById(R.id.btnVolverPerfilSocio)

        btnImprimirCarnet.setOnClickListener {
            Toast.makeText(this, "Impresión prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnCompartirCarnet.setOnClickListener {
            Toast.makeText(this, "Compartir carnet previsto para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnVolverPerfilSocio.setOnClickListener {
            finish()
        }
    }
}