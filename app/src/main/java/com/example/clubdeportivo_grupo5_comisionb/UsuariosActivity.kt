package com.example.clubdeportivo_grupo5_comisionb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        val btnVolverUsuarios: Button = findViewById(R.id.btnVolverUsuarios)
        val txtMenuUsuarios: TextView = findViewById(R.id.txtMenuUsuarios)
        val btnRegistrarUsuario: Button = findViewById(R.id.btnRegistrarUsuario)
        val btnVerKevin: Button = findViewById(R.id.btnVerKevin)
        val btnBajaEric: Button = findViewById(R.id.btnBajaEric)
        val btnBajaFernando: Button = findViewById(R.id.btnBajaFernando)

        btnVolverUsuarios.setOnClickListener {
            finish()
        }

        txtMenuUsuarios.setOnClickListener {
            Toast.makeText(this, "Opciones de usuario disponibles en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnRegistrarUsuario.setOnClickListener {
            Toast.makeText(this, "Registro de usuario disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnVerKevin.setOnClickListener {
            Toast.makeText(this, "Perfil de usuario disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnBajaEric.setOnClickListener {
            Toast.makeText(this, "Baja de usuario disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnBajaFernando.setOnClickListener {
            Toast.makeText(this, "Baja de usuario disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }
    }
}