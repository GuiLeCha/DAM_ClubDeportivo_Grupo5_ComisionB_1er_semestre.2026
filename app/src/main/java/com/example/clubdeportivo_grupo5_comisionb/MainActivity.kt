package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.database.sqlite.SQLiteDatabase

class MainActivity : AppCompatActivity() {

    // TEMPORAL PARA DESARROLLO:
    private lateinit var dbHelperPrueba: SQLiteHelper
    private lateinit var dbAbiertaPrueba: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    // TEMPORAL PARA DESARROLLO:
    // Mantiene abierta la base para poder verla en App Inspection.
        dbHelperPrueba = SQLiteHelper(this)
        dbAbiertaPrueba = dbHelperPrueba.writableDatabase

        val edtUsuario: EditText = findViewById(R.id.edtUsuario)
        val edtClave: EditText = findViewById(R.id.edtClave)
        val btnIniciarSesion: Button = findViewById(R.id.btnIniciarSesion)
        val txtOlvideClave: TextView = findViewById(R.id.txtOlvideClave)
        val txtGoogle: TextView = findViewById(R.id.txtGoogle)
        val txtOutlook: TextView = findViewById(R.id.txtOutlook)

        btnIniciarSesion.setOnClickListener {
            val usuario = edtUsuario.text.toString().trim()
            val clave = edtClave.text.toString().trim()

            if (usuario.isEmpty()) {
                edtUsuario.error = "Ingrese el usuario"
                edtUsuario.requestFocus()
                return@setOnClickListener
            }

            if (clave.isEmpty()) {
                edtClave.error = "Ingrese la contraseña"
                edtClave.requestFocus()
                return@setOnClickListener
            }

            if (usuario == "admin" && clave == "1234") {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        txtOlvideClave.setOnClickListener {
            Toast.makeText(this, "Recuperación de clave disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        txtGoogle.setOnClickListener {
            Toast.makeText(this, "Ingreso con Google disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        txtOutlook.setOnClickListener {
            Toast.makeText(this, "Ingreso con Outlook disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }
    }
}