package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIniciarSesion: Button = findViewById(R.id.btnIniciarSesion)
        val txtGoogle: TextView = findViewById(R.id.txtGoogle)
        val txtOutlook: TextView = findViewById(R.id.txtOutlook)

        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        txtGoogle.setOnClickListener {
            Toast.makeText(this, "Ingreso con Google disponible próximamente", Toast.LENGTH_SHORT).show()
        }

        txtOutlook.setOnClickListener {
            Toast.makeText(this, "Ingreso con Outlook disponible próximamente", Toast.LENGTH_SHORT).show()
        }
    }
}