package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrarPagoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_pago)

        val btnVolverRegistrarPago: Button = findViewById(R.id.btnVolverRegistrarPago)

        val edtConceptoPago: EditText = findViewById(R.id.edtConceptoPago)
        val edtMontoPago: EditText = findViewById(R.id.edtMontoPago)

        val chkEfectivo: CheckBox = findViewById(R.id.chkEfectivo)
        val chkTarjeta: CheckBox = findViewById(R.id.chkTarjeta)
        val chkTransferencia: CheckBox = findViewById(R.id.chkTransferencia)

        val btnRegistrarPago: Button = findViewById(R.id.btnRegistrarPago)

        btnVolverRegistrarPago.setOnClickListener {
            finish()
        }

        btnRegistrarPago.setOnClickListener {
            val concepto = edtConceptoPago.text.toString().trim()
            val montoTexto = edtMontoPago.text.toString().trim()

            if (concepto.isEmpty()) {
                edtConceptoPago.error = "Ingrese el concepto"
                edtConceptoPago.requestFocus()
                return@setOnClickListener
            }

            if (montoTexto.isEmpty()) {
                edtMontoPago.error = "Ingrese el monto"
                edtMontoPago.requestFocus()
                return@setOnClickListener
            }

            val monto = montoTexto.toDoubleOrNull()
            if (monto == null || monto <= 0) {
                edtMontoPago.error = "Ingrese un monto válido"
                edtMontoPago.requestFocus()
                return@setOnClickListener
            }

            val cantidadMediosSeleccionados = listOf(
                chkEfectivo.isChecked,
                chkTarjeta.isChecked,
                chkTransferencia.isChecked
            ).count { it }

            if (cantidadMediosSeleccionados == 0) {
                Toast.makeText(this, "Seleccione un medio de pago", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cantidadMediosSeleccionados > 1) {
                Toast.makeText(this, "Seleccione solo un medio de pago", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, PagoExitosoActivity::class.java)
            startActivity(intent)
        }
    }
}