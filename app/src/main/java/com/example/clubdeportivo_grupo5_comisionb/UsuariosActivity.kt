package com.example.clubdeportivo_grupo5_comisionb

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        val btnRegistrarUsuario: Button = findViewById(R.id.btnRegistrarUsuario)
        val btnVerKevin: Button = findViewById(R.id.btnVerKevin)
        val btnBajaEric: Button = findViewById(R.id.btnBajaEric)
        val btnBajaFernando: Button = findViewById(R.id.btnBajaFernando)

        btnRegistrarUsuario.setOnClickListener {
            Toast.makeText(this, "Registro de usuario previsto para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnVerKevin.setOnClickListener {
            Toast.makeText(this, "Perfil de usuario previsto para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnBajaEric.setOnClickListener {
            Toast.makeText(this, "Baja de usuario prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnBajaFernando.setOnClickListener {
            Toast.makeText(this, "Baja de usuario prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }
    }
}