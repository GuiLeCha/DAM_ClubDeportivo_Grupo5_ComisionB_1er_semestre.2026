package com.example.clubdeportivo_grupo5_comisionb

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VencimientosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vencimientos)

        val btnVolverVencimientos: Button = findViewById(R.id.btnVolverVencimientos)
        val txtMenuVencimientos: TextView = findViewById(R.id.txtMenuVencimientos)
        val btnNotificarJulia: Button = findViewById(R.id.btnNotificarJulia)
        val btnNotificarMariana: Button = findViewById(R.id.btnNotificarMariana)

        btnVolverVencimientos.setOnClickListener {
            finish()
        }

        txtMenuVencimientos.setOnClickListener {
            Toast.makeText(this, "Filtros de vencimientos disponibles en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnNotificarJulia.setOnClickListener {
            Toast.makeText(this, "Notificación a Julia disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnNotificarMariana.setOnClickListener {
            Toast.makeText(this, "Notificación a Mariana disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }
    }
}