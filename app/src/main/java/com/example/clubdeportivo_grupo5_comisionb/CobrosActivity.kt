package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CobrosActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var edtBuscarCobros: EditText
    private lateinit var txtSubtituloCobros: TextView
    private lateinit var recyclerCobros: RecyclerView
    private lateinit var cobroAdapter: CobroAdapter

    private val listaCobros = mutableListOf<CobroItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cobros)

        dbHelper = SQLiteHelper(this)

        val btnVolverCobros: Button = findViewById(R.id.btnVolverCobros)
        val btnBuscarCobros: Button = findViewById(R.id.btnBuscarCobros)
        val btnLimpiarBusquedaCobros: Button = findViewById(R.id.btnLimpiarBusquedaCobros)

        edtBuscarCobros = findViewById(R.id.edtBuscarCobros)
        txtSubtituloCobros = findViewById(R.id.txtSubtituloCobros)
        recyclerCobros = findViewById(R.id.recyclerCobros)

        configurarRecyclerView()

        btnVolverCobros.setOnClickListener {
            finish()
        }

        btnBuscarCobros.setOnClickListener {
            buscarCobros()
        }

        btnLimpiarBusquedaCobros.setOnClickListener {
            edtBuscarCobros.setText("")
            cargarCobros()
        }

        cargarCobros()
    }

    override fun onResume() {
        super.onResume()

        if (::edtBuscarCobros.isInitialized && edtBuscarCobros.text.toString().trim().isEmpty()) {
            cargarCobros()
        }
    }

    private fun configurarRecyclerView() {
        cobroAdapter = CobroAdapter(listaCobros) { cobro ->
            abrirPantallaPago(cobro)
        }

        recyclerCobros.layoutManager = LinearLayoutManager(this)
        recyclerCobros.adapter = cobroAdapter
    }

    private fun cargarCobros() {
        val socios = dbHelper.obtenerSocios()
        val visitantes = dbHelper.obtenerVisitantes()
        val cobros = armarListaCobros(socios, visitantes)

        mostrarCobros(
            cobros,
            "Seleccione una persona para registrar el pago"
        )
    }

    private fun buscarCobros() {
        val textoBusqueda = edtBuscarCobros.text.toString().trim()

        if (textoBusqueda.isEmpty()) {
            edtBuscarCobros.error = "Ingrese un dato para buscar"
            edtBuscarCobros.requestFocus()
            return
        }

        val socios = dbHelper.buscarSocios(textoBusqueda)
        val visitantes = dbHelper.buscarVisitantes(textoBusqueda)
        val cobros = armarListaCobros(socios, visitantes)

        val mensajeEstado = if (cobros.isEmpty()) {
            "No se encontraron personas para: $textoBusqueda"
        } else {
            "Resultados para '$textoBusqueda': ${cobros.size}"
        }

        mostrarCobros(cobros, mensajeEstado)
    }

    private fun armarListaCobros(
        socios: MutableList<Socio>,
        visitantes: MutableList<Visitante>
    ): MutableList<CobroItem> {
        val cobros = mutableListOf<CobroItem>()

        for (socio in socios) {
            val item = CobroItem(
                tipoCliente = "SOCIO",
                clienteId = socio.id,
                nombreCliente = socio.nombreCompleto,
                dniCliente = socio.dni,
                concepto = "Cuota mensual",
                monto = 12000.0,
                detalle = "Vencimiento actual: ${socio.fechaVencimientoCuota}"
            )
            cobros.add(item)
        }

        for (visitante in visitantes) {
            val item = CobroItem(
                tipoCliente = "VISITANTE",
                clienteId = visitante.id,
                nombreCliente = visitante.nombreCompleto,
                dniCliente = visitante.dni,
                concepto = "Actividad diaria",
                monto = 3500.0,
                detalle = "Actividad: ${visitante.actividad}"
            )
            cobros.add(item)
        }

        return cobros
    }

    private fun mostrarCobros(cobros: MutableList<CobroItem>, mensajeEstado: String) {
        txtSubtituloCobros.text = if (cobros.isEmpty()) {
            "$mensajeEstado - No hay personas para mostrar"
        } else {
            mensajeEstado
        }

        cobroAdapter.actualizarLista(cobros)
    }

    private fun abrirPantallaPago(cobro: CobroItem) {
        val intent = Intent(this, RegistrarPagoActivity::class.java)
        intent.putExtra("tipoCliente", cobro.tipoCliente)
        intent.putExtra("clienteId", cobro.clienteId)
        intent.putExtra("nombreCliente", cobro.nombreCliente)
        intent.putExtra("dniCliente", cobro.dniCliente)
        intent.putExtra("concepto", cobro.concepto)
        intent.putExtra("monto", cobro.monto)
        startActivity(intent)
    }
}