package com.example.clubdeportivo_grupo5_comisionb

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrarPagoActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper

    private var tipoCliente: String = ""
    private var clienteId: Int = 0
    private var nombreCliente: String = ""
    private var dniCliente: String = ""
    private var conceptoInicial: String = ""
    private var montoInicial: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_pago)

        dbHelper = SQLiteHelper(this)

        val btnVolverRegistrarPago: Button = findViewById(R.id.btnVolverRegistrarPago)

        val txtClientePago: TextView = findViewById(R.id.txtClientePago)
        val txtDetalleClientePago: TextView = findViewById(R.id.txtDetalleClientePago)

        val edtConceptoPago: EditText = findViewById(R.id.edtConceptoPago)
        val edtMontoPago: EditText = findViewById(R.id.edtMontoPago)

        val chkEfectivo: CheckBox = findViewById(R.id.chkEfectivo)
        val chkTarjeta: CheckBox = findViewById(R.id.chkTarjeta)
        val chkTransferencia: CheckBox = findViewById(R.id.chkTransferencia)

        val btnRegistrarPago: Button = findViewById(R.id.btnRegistrarPago)

        cargarDatosRecibidos()

        if (!datosPagoValidos()) {
            Toast.makeText(
                this,
                "No se recibieron los datos necesarios para registrar el pago",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        txtClientePago.text = nombreCliente
        txtDetalleClientePago.text = "$tipoCliente - DNI: $dniCliente"
        edtConceptoPago.setText(conceptoInicial)
        edtMontoPago.setText(montoInicial.toInt().toString())

        btnVolverRegistrarPago.setOnClickListener {
            finish()
        }

        chkEfectivo.setOnClickListener {
            if (chkEfectivo.isChecked) {
                chkTarjeta.isChecked = false
                chkTransferencia.isChecked = false
            }
        }

        chkTarjeta.setOnClickListener {
            if (chkTarjeta.isChecked) {
                chkEfectivo.isChecked = false
                chkTransferencia.isChecked = false
            }
        }

        chkTransferencia.setOnClickListener {
            if (chkTransferencia.isChecked) {
                chkEfectivo.isChecked = false
                chkTarjeta.isChecked = false
            }
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

            val medioPago = obtenerMedioPago(chkEfectivo, chkTarjeta, chkTransferencia)

            if (medioPago.isEmpty()) {
                Toast.makeText(this, "Seleccione un medio de pago", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tipoCliente == "SOCIO") {
                preguntarEntregaCarnet(concepto, monto, medioPago)
            } else {
                guardarPago(concepto, monto, medioPago, false)
            }
        }
    }

    private fun cargarDatosRecibidos() {
        tipoCliente = intent.getStringExtra("tipoCliente") ?: ""
        clienteId = intent.getIntExtra("clienteId", 0)
        nombreCliente = intent.getStringExtra("nombreCliente") ?: ""
        dniCliente = intent.getStringExtra("dniCliente") ?: ""
        conceptoInicial = intent.getStringExtra("concepto") ?: ""
        montoInicial = intent.getDoubleExtra("monto", 0.0)
    }

    private fun datosPagoValidos(): Boolean {
        if (tipoCliente != "SOCIO" && tipoCliente != "VISITANTE") {
            return false
        }

        if (clienteId <= 0) {
            return false
        }

        if (nombreCliente.isEmpty()) {
            return false
        }

        if (dniCliente.isEmpty()) {
            return false
        }

        if (conceptoInicial.isEmpty()) {
            return false
        }

        if (montoInicial <= 0) {
            return false
        }

        return true
    }

    private fun obtenerMedioPago(
        chkEfectivo: CheckBox,
        chkTarjeta: CheckBox,
        chkTransferencia: CheckBox
    ): String {
        return when {
            chkEfectivo.isChecked -> "Efectivo"
            chkTarjeta.isChecked -> "Tarjeta"
            chkTransferencia.isChecked -> "Transferencia"
            else -> ""
        }
    }

    private fun preguntarEntregaCarnet(
        concepto: String,
        monto: Double,
        medioPago: String
    ) {
        AlertDialog.Builder(this)
            .setTitle("Carnet de socio")
            .setMessage("¿Desea entregar carnet al socio junto con el pago?")
            .setPositiveButton("Sí") { _, _ ->
                guardarPago(concepto, monto, medioPago, true)
            }
            .setNegativeButton("No") { _, _ ->
                guardarPago(concepto, monto, medioPago, false)
            }
            .show()
    }

    private fun guardarPago(
        concepto: String,
        monto: Double,
        medioPago: String,
        carnetEntregado: Boolean
    ) {
        val fechaPago = dbHelper.obtenerFechaHoy()

        val fechaVencimiento = if (tipoCliente == "SOCIO") {
            dbHelper.calcularNuevoVencimientoSocio(clienteId)
        } else {
            ""
        }

        val pago = Pago(
            tipoCliente = tipoCliente,
            clienteId = clienteId,
            nombreCliente = nombreCliente,
            dniCliente = dniCliente,
            concepto = concepto,
            monto = monto,
            medioPago = medioPago,
            fechaPago = fechaPago,
            fechaVencimientoGenerada = fechaVencimiento
        )

        val resultado = dbHelper.insertarPago(pago)

        if (resultado > 0) {
            if (tipoCliente == "SOCIO") {
                dbHelper.actualizarVencimientoSocioPorId(
                    clienteId,
                    fechaVencimiento,
                    carnetEntregado
                )
            }

            Toast.makeText(this, "Pago registrado correctamente", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PagoExitosoActivity::class.java)
            intent.putExtra("pagoId", resultado.toInt())
            intent.putExtra("tipoCliente", tipoCliente)
            intent.putExtra("clienteId", clienteId)
            intent.putExtra("nombreCliente", nombreCliente)
            intent.putExtra("dniCliente", dniCliente)
            intent.putExtra("concepto", concepto)
            intent.putExtra("monto", monto)
            intent.putExtra("medioPago", medioPago)
            intent.putExtra("fechaPago", fechaPago)
            intent.putExtra("fechaVencimiento", fechaVencimiento)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "No se pudo registrar el pago", Toast.LENGTH_SHORT).show()
        }
    }
}