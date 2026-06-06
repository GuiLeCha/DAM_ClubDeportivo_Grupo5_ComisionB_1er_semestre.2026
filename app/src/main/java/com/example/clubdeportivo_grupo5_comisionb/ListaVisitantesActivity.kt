package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListaVisitantesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_visitantes)

        val btnVolverListaVisitantes: Button = findViewById(R.id.btnVolverListaVisitantes)
        val txtMenuListaVisitantes: TextView = findViewById(R.id.txtMenuListaVisitantes)
        val btnPerfilFernando: Button = findViewById(R.id.btnPerfilFernando)
        val btnPerfilEricVisitante: Button = findViewById(R.id.btnPerfilEricVisitante)

        btnVolverListaVisitantes.setOnClickListener {
            finish()
        }

        txtMenuListaVisitantes.setOnClickListener {
            Toast.makeText(this, "Filtros avanzados disponibles en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnPerfilFernando.setOnClickListener {
            val intent = Intent(this, PerfilVisitanteActivity::class.java)
            startActivity(intent)
        }

        btnPerfilEricVisitante.setOnClickListener {
            Toast.makeText(this, "Ficha de Eric disponible en próximas etapas", Toast.LENGTH_SHORT).show()
        }
    }
}