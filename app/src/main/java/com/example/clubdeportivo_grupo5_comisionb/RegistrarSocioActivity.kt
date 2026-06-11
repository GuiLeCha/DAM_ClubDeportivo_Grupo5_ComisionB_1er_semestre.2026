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

        val dbHelper = SQLiteHelper(this)

        val btnVolverRegistrarSocio: Button = findViewById(R.id.btnVolverRegistrarSocio)
        val edtNombreSocio: EditText = findViewById(R.id.edtNombreSocio)
        val edtApellidoSocio: EditText = findViewById(R.id.edtApellidoSocio)
        val edtDniSocio: EditText = findViewById(R.id.edtDniSocio)
        val edtFechaNacimientoSocio: EditText = findViewById(R.id.edtFechaNacimientoSocio)
        val edtTelefonoSocio: EditText = findViewById(R.id.edtTelefonoSocio)
        val edtEmailSocio: EditText = findViewById(R.id.edtEmailSocio)
        val edtDireccionSocio: EditText = findViewById(R.id.edtDireccionSocio)
        val chkAptoFisicoSocio: CheckBox = findViewById(R.id.chkAptoFisicoSocio)

        val btnAgregarSocio: Button = findViewById(R.id.btnAgregarSocio)

        btnVolverRegistrarSocio.setOnClickListener {
            finish()
        }

        btnAgregarSocio.setOnClickListener {
            val nombre = edtNombreSocio.text.toString().trim()
            val apellido = edtApellidoSocio.text.toString().trim()
            val dni = edtDniSocio.text.toString().trim()
            val fechaNacimiento = edtFechaNacimientoSocio.text.toString().trim()
            val telefono = edtTelefonoSocio.text.toString().trim()
            val email = edtEmailSocio.text.toString().trim()
            val direccion = edtDireccionSocio.text.toString().trim()

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

            if (dbHelper.existeDniSocio(dni) || dbHelper.existeDniVisitante(dni)) {
                edtDniSocio.error = "El DNI ya se encuentra registrado"
                edtDniSocio.requestFocus()
                Toast.makeText(this, "Ya existe una persona registrada con ese DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val socio = Socio(
                nombre = nombre,
                apellido = apellido,
                dni = dni,
                telefono = telefono,
                email = email,
                direccion = direccion,
                fechaNacimiento = fechaNacimiento,
                aptoFisico = true,
                carnetEntregado = false,
                fechaAlta = dbHelper.obtenerFechaHoy(),
                fechaVencimientoCuota = dbHelper.obtenerFechaHoy()
            )

            val resultado = dbHelper.insertarSocio(socio)

            if (resultado > 0) {
                Toast.makeText(this, "Socio registrado correctamente", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, RegistrarPagoActivity::class.java)
                intent.putExtra("tipoCliente", "SOCIO")
                intent.putExtra("clienteId", resultado.toInt())
                intent.putExtra("nombreCliente", "$nombre $apellido")
                intent.putExtra("dniCliente", dni)
                intent.putExtra("concepto", "Cuota mensual")
                intent.putExtra("monto", 12000.0)
                intent.putExtra("carnetEntregado", false)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "No se pudo registrar el socio", Toast.LENGTH_SHORT).show()
            }
        }
    }
}