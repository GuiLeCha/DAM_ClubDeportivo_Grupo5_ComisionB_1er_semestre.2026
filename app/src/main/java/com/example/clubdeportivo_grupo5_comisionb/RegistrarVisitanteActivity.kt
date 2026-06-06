package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrarVisitanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_visitante)

        val btnVolverRegistrarVisitante: Button = findViewById(R.id.btnVolverRegistrarVisitante)
        val txtMenuSimuladoVisitante: TextView = findViewById(R.id.txtMenuSimuladoVisitante)

        val edtNombreVisitante: EditText = findViewById(R.id.edtNombreVisitante)
        val edtApellidoVisitante: EditText = findViewById(R.id.edtApellidoVisitante)
        val edtDniVisitante: EditText = findViewById(R.id.edtDniVisitante)
        val edtActividadVisitante: EditText = findViewById(R.id.edtActividadVisitante)

        val btnAgregarVisitante: Button = findViewById(R.id.btnAgregarVisitante)

        btnVolverRegistrarVisitante.setOnClickListener {
            finish()
        }

        txtMenuSimuladoVisitante.setOnClickListener {
            Toast.makeText(this, "Menú de opciones disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnAgregarVisitante.setOnClickListener {
            val nombre = edtNombreVisitante.text.toString().trim()
            val apellido = edtApellidoVisitante.text.toString().trim()
            val dni = edtDniVisitante.text.toString().trim()
            val actividad = edtActividadVisitante.text.toString().trim()

            if (nombre.isEmpty()) {
                edtNombreVisitante.error = "Ingrese el nombre"
                edtNombreVisitante.requestFocus()
                return@setOnClickListener
            }

            if (apellido.isEmpty()) {
                edtApellidoVisitante.error = "Ingrese el apellido"
                edtApellidoVisitante.requestFocus()
                return@setOnClickListener
            }

            if (dni.isEmpty()) {
                edtDniVisitante.error = "Ingrese el DNI"
                edtDniVisitante.requestFocus()
                return@setOnClickListener
            }

            if (actividad.isEmpty()) {
                edtActividadVisitante.error = "Ingrese la actividad"
                edtActividadVisitante.requestFocus()
                return@setOnClickListener
            }

            val intent = Intent(this, RegistroExitosoActivity::class.java)
            startActivity(intent)
        }
    }
}