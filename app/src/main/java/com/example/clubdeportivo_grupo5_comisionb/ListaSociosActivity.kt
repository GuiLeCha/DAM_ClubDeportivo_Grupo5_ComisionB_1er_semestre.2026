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

class ListaSociosActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var txtEstadoListaSocios: TextView
    private lateinit var edtBuscarSocios: EditText
    private lateinit var recyclerSocios: RecyclerView
    private lateinit var socioAdapter: SocioAdapter

    private val listaSocios = mutableListOf<Socio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_socios)

        dbHelper = SQLiteHelper(this)

        val btnVolverListaSocios: Button = findViewById(R.id.btnVolverListaSocios)
        val btnBuscarSocios: Button = findViewById(R.id.btnBuscarSocios)
        val btnLimpiarBusquedaSocios: Button = findViewById(R.id.btnLimpiarBusquedaSocios)

        edtBuscarSocios = findViewById(R.id.edtBuscarSocios)
        txtEstadoListaSocios = findViewById(R.id.txtEstadoListaSocios)
        recyclerSocios = findViewById(R.id.recyclerSocios)

        configurarRecyclerView()

        btnVolverListaSocios.setOnClickListener {
            finish()
        }

        btnBuscarSocios.setOnClickListener {
            buscarSocios()
        }

        btnLimpiarBusquedaSocios.setOnClickListener {
            edtBuscarSocios.setText("")
            cargarSocios()
        }

        cargarSocios()
    }

    override fun onResume() {
        super.onResume()

        if (::edtBuscarSocios.isInitialized && edtBuscarSocios.text.toString().trim().isEmpty()) {
            cargarSocios()
        }
    }

    private fun configurarRecyclerView() {
        socioAdapter = SocioAdapter(
            listaSocios,
            alHacerClickPerfil = { socio ->
                val intent = Intent(this, PerfilSocioActivity::class.java)
                intent.putExtra("socioId", socio.id)
                startActivity(intent)
            },
            alHacerClickBaja = { socio ->
                confirmarBajaSocio(socio)
            }
        )

        recyclerSocios.layoutManager = LinearLayoutManager(this)
        recyclerSocios.adapter = socioAdapter
    }

    private fun cargarSocios() {
        val socios = dbHelper.obtenerSocios()
        mostrarSocios(socios, "Socios registrados: ${socios.size}")
    }

    private fun buscarSocios() {
        val textoBusqueda = edtBuscarSocios.text.toString().trim()

        if (textoBusqueda.isEmpty()) {
            edtBuscarSocios.error = "Ingrese un dato para buscar"
            edtBuscarSocios.requestFocus()
            return
        }

        val socios = dbHelper.buscarSocios(textoBusqueda)

        val mensajeEstado = if (socios.isEmpty()) {
            "No se encontraron socios para: $textoBusqueda"
        } else {
            "Resultados para '$textoBusqueda': ${socios.size}"
        }

        mostrarSocios(socios, mensajeEstado)
    }

    private fun mostrarSocios(socios: MutableList<Socio>, mensajeEstado: String) {
        txtEstadoListaSocios.text = if (socios.isEmpty()) {
            "$mensajeEstado - No hay socios para mostrar"
        } else {
            mensajeEstado
        }

        socioAdapter.actualizarLista(socios)
    }

    private fun confirmarBajaSocio(socio: Socio) {
        AlertDialog.Builder(this)
            .setTitle("Dar de baja socio")
            .setMessage("¿Confirmás dar de baja a ${socio.nombreCompleto}? El historial de pagos se conservará.")
            .setPositiveButton("Sí") { _, _ ->
                val resultado = dbHelper.darDeBajaSocio(socio.id)

                if (resultado > 0) {
                    Toast.makeText(this, "Socio dado de baja correctamente", Toast.LENGTH_SHORT).show()
                    refrescarListaActual()
                } else {
                    Toast.makeText(this, "No se pudo dar de baja el socio", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun refrescarListaActual() {
        val textoBusqueda = edtBuscarSocios.text.toString().trim()

        if (textoBusqueda.isEmpty()) {
            cargarSocios()
        } else {
            buscarSocios()
        }
    }
}