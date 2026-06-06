package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrarSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_socio)

        val btnVolverRegistrarSocio: Button = findViewById(R.id.btnVolverRegistrarSocio)
        val txtMenuSimuladoSocio: TextView = findViewById(R.id.txtMenuSimuladoSocio)

        val edtNombreSocio: EditText = findViewById(R.id.edtNombreSocio)
        val edtApellidoSocio: EditText = findViewById(R.id.edtApellidoSocio)
        val edtDniSocio: EditText = findViewById(R.id.edtDniSocio)
        val edtTelefonoSocio: EditText = findViewById(R.id.edtTelefonoSocio)
        val edtEmailSocio: EditText = findViewById(R.id.edtEmailSocio)
        val chkAptoFisicoSocio: CheckBox = findViewById(R.id.chkAptoFisicoSocio)

        val btnAgregarSocio: Button = findViewById(R.id.btnAgregarSocio)

        btnVolverRegistrarSocio.setOnClickListener {
            finish()
        }

        txtMenuSimuladoSocio.setOnClickListener {
            Toast.makeText(this, "Menú de opciones disponible en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnAgregarSocio.setOnClickListener {
            val nombre = edtNombreSocio.text.toString().trim()
            val apellido = edtApellidoSocio.text.toString().trim()
            val dni = edtDniSocio.text.toString().trim()
            val telefono = edtTelefonoSocio.text.toString().trim()
            val email = edtEmailSocio.text.toString().trim()

            if (nombre.isEmpty()) {
                edtNombreSocio.error = "Ingrese el nombre"
                edtNombreSocio.requestFocus()
                return@setOnClickListener
            }

            if (apellido.isEmpty()) {
                edtApellidoSocio.error = "Ingrese el apellido"
                edtApellidoSocio.requestFocus()
                return@setOnClickListener
            }

            if (dni.isEmpty()) {
                edtDniSocio.error = "Ingrese el DNI"
                edtDniSocio.requestFocus()
                return@setOnClickListener
            }

            if (telefono.isEmpty() && email.isEmpty()) {
                edtTelefonoSocio.error = "Ingrese teléfono o email"
                edtTelefonoSocio.requestFocus()
                Toast.makeText(this, "Debe completar al menos un dato de contacto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!chkAptoFisicoSocio.isChecked) {
                Toast.makeText(this, "Debe confirmar el apto físico del socio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, RegistroExitosoActivity::class.java)
            startActivity(intent)
        }
    }
}