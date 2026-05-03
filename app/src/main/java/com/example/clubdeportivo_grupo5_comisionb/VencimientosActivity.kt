package com.example.clubdeportivo_grupo5_comisionb

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VencimientosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vencimientos)

        val btnNotificarJulia: Button = findViewById(R.id.btnNotificarJulia)
        val btnNotificarMariana: Button = findViewById(R.id.btnNotificarMariana)

        btnNotificarJulia.setOnClickListener {
            Toast.makeText(this, "Notificación prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }

        btnNotificarMariana.setOnClickListener {
            Toast.makeText(this, "Notificación prevista para próxima etapa", Toast.LENGTH_SHORT).show()
        }
    }
}