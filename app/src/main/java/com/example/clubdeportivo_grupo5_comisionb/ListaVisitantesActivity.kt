package com.example.clubdeportivo_grupo5_comisionb

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaVisitantesActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var txtEstadoListaVisitantes: TextView
    private lateinit var edtBuscarVisitantes: EditText
    private lateinit var recyclerVisitantes: RecyclerView
    private lateinit var visitanteAdapter: VisitanteAdapter

    private val listaVisitantes = mutableListOf<Visitante>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_visitantes)

        dbHelper = SQLiteHelper(this)

        val btnVolverListaVisitantes: Button = findViewById(R.id.btnVolverListaVisitantes)
        val txtMenuListaVisitantes: TextView = findViewById(R.id.txtMenuListaVisitantes)
        val btnBuscarVisitantes: Button = findViewById(R.id.btnBuscarVisitantes)
        val btnLimpiarBusquedaVisitantes: Button = findViewById(R.id.btnLimpiarBusquedaVisitantes)

        edtBuscarVisitantes = findViewById(R.id.edtBuscarVisitantes)
        txtEstadoListaVisitantes = findViewById(R.id.txtEstadoListaVisitantes)
        recyclerVisitantes = findViewById(R.id.recyclerVisitantes)

        configurarRecyclerView()

        btnVolverListaVisitantes.setOnClickListener {
            finish()
        }

        txtMenuListaVisitantes.setOnClickListener {
            Toast.makeText(this, "Filtros avanzados disponibles en futuras versiones", Toast.LENGTH_SHORT).show()
        }

        btnBuscarVisitantes.setOnClickListener {
            buscarVisitantes()
        }

        btnLimpiarBusquedaVisitantes.setOnClickListener {
            edtBuscarVisitantes.setText("")
            cargarVisitantes()
        }

        cargarVisitantes()
    }

    override fun onResume() {
        super.onResume()

        if (::edtBuscarVisitantes.isInitialized && edtBuscarVisitantes.text.toString().trim().isEmpty()) {
            cargarVisitantes()
        }
    }

    private fun configurarRecyclerView() {
        visitanteAdapter = VisitanteAdapter(
            listaVisitantes,
            alHacerClickPerfil = { visitante ->
                val intent = Intent(this, PerfilVisitanteActivity::class.java)
                intent.putExtra("visitanteId", visitante.id)
                startActivity(intent)
            },
            alHacerClickBaja = { visitante ->
                confirmarBajaVisitante(visitante)
            }
        )

        recyclerVisitantes.layoutManager = LinearLayoutManager(this)
        recyclerVisitantes.adapter = visitanteAdapter
    }

    private fun cargarVisitantes() {
        val visitantes = dbHelper.obtenerVisitantes()
        mostrarVisitantes(visitantes, "Visitantes registrados: ${visitantes.size}")
    }

    private fun buscarVisitantes() {
        val textoBusqueda = edtBuscarVisitantes.text.toString().trim()

        if (textoBusqueda.isEmpty()) {
            edtBuscarVisitantes.error = "Ingrese un dato para buscar"
            edtBuscarVisitantes.requestFocus()
            return
        }

        val visitantes = dbHelper.buscarVisitantes(textoBusqueda)

        val mensajeEstado = if (visitantes.isEmpty()) {
            "No se encontraron visitantes para: $textoBusqueda"
        } else {
            "Resultados para '$textoBusqueda': ${visitantes.size}"
        }

        mostrarVisitantes(visitantes, mensajeEstado)
    }

    private fun mostrarVisitantes(visitantes: MutableList<Visitante>, mensajeEstado: String) {
        txtEstadoListaVisitantes.text = if (visitantes.isEmpty()) {
            "$mensajeEstado - No hay visitantes para mostrar"
        } else {
            mensajeEstado
        }

        visitanteAdapter.actualizarLista(visitantes)
    }

    private fun confirmarBajaVisitante(visitante: Visitante) {
        AlertDialog.Builder(this)
            .setTitle("Dar de baja visitante")
            .setMessage("¿Confirmás dar de baja a ${visitante.nombreCompleto}? El historial de pagos se conservará.")
            .setPositiveButton("Sí") { _, _ ->
                val resultado = dbHelper.darDeBajaVisitante(visitante.id)

                if (resultado > 0) {
                    Toast.makeText(this, "Visitante dado de baja correctamente", Toast.LENGTH_SHORT).show()
                    refrescarListaActual()
                } else {
                    Toast.makeText(this, "No se pudo dar de baja el visitante", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun refrescarListaActual() {
        val textoBusqueda = edtBuscarVisitantes.text.toString().trim()

        if (textoBusqueda.isEmpty()) {
            cargarVisitantes()
        } else {
            buscarVisitantes()
        }
    }
}