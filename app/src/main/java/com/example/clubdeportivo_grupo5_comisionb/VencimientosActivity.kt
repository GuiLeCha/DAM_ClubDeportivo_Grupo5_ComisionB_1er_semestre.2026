package com.example.clubdeportivo_grupo5_comisionb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VencimientosActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var txtEstadoVencimientos: TextView
    private lateinit var recyclerVencimientos: RecyclerView
    private lateinit var vencimientoAdapter: VencimientoAdapter

    private val listaVencimientos = mutableListOf<Socio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vencimientos)

        dbHelper = SQLiteHelper(this)

        val btnVolverVencimientos: Button = findViewById(R.id.btnVolverVencimientos)

        txtEstadoVencimientos = findViewById(R.id.txtEstadoVencimientos)
        recyclerVencimientos = findViewById(R.id.recyclerVencimientos)

        configurarRecyclerView()

        btnVolverVencimientos.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        cargarVencimientosDelDia()
    }

    private fun configurarRecyclerView() {
        vencimientoAdapter = VencimientoAdapter(
            listaVencimientos,
            alHacerClickCobrar = { socio ->
                abrirPantallaPago(socio)
            },
            alHacerClickNotificar = {
                Toast.makeText(
                    this,
                    "Notificación prevista para futuras versiones",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        recyclerVencimientos.layoutManager = LinearLayoutManager(this)
        recyclerVencimientos.adapter = vencimientoAdapter
    }

    private fun cargarVencimientosDelDia() {
        val fechaHoy = dbHelper.obtenerFechaHoy()
        val sociosConVencimiento = dbHelper.obtenerVencimientosDelDia()

        txtEstadoVencimientos.text = if (sociosConVencimiento.isEmpty()) {
            "No hay cuotas que venzan hoy ($fechaHoy)"
        } else {
            "Cuotas que vencen hoy ($fechaHoy): ${sociosConVencimiento.size}"
        }

        vencimientoAdapter.actualizarLista(sociosConVencimiento)
    }

    private fun abrirPantallaPago(socio: Socio) {
        val intent = Intent(this, RegistrarPagoActivity::class.java)
        intent.putExtra("tipoCliente", "SOCIO")
        intent.putExtra("clienteId", socio.id)
        intent.putExtra("nombreCliente", socio.nombreCompleto)
        intent.putExtra("dniCliente", socio.dni)
        intent.putExtra("concepto", "Cuota mensual")
        intent.putExtra("monto", 12000.0)
        startActivity(intent)
    }
}