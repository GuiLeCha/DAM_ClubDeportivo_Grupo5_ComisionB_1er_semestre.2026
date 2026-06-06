package com.example.clubdeportivo_grupo5_comisionb

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnRegistrarCliente: Button = findViewById(R.id.btnRegistrarCliente)
        val btnBuscarCliente: Button = findViewById(R.id.btnBuscarCliente)
        val btnCobros: Button = findViewById(R.id.btnCobros)
        val btnVencimientos: Button = findViewById(R.id.btnVencimientos)
        val btnUsuarios: Button = findViewById(R.id.btnUsuarios)

        btnRegistrarCliente.setOnClickListener {
            mostrarDialogRegistro()
        }

        btnBuscarCliente.setOnClickListener {
            mostrarDialogBusqueda()
        }

        btnCobros.setOnClickListener {
            val intent = Intent(this, CobrosActivity::class.java)
            startActivity(intent)
        }

        btnVencimientos.setOnClickListener {
            val intent = Intent(this, VencimientosActivity::class.java)
            startActivity(intent)
        }

        btnUsuarios.setOnClickListener {
            val intent = Intent(this, UsuariosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDialogRegistro() {
        val vista = LayoutInflater.from(this).inflate(R.layout.dialog_tipo_registro, null)
        val dialog = AlertDialog.Builder(this).setView(vista).create()

        val txtCerrarRegistro: TextView = vista.findViewById(R.id.txtCerrarRegistro)
        val cardSocioRegistro: LinearLayout = vista.findViewById(R.id.cardSocioRegistro)
        val cardVisitanteRegistro: LinearLayout = vista.findViewById(R.id.cardVisitanteRegistro)
        val btnContinuarRegistroDialog: Button = vista.findViewById(R.id.btnContinuarRegistroDialog)

        cardSocioRegistro.setOnClickListener {
            val intent = Intent(this, RegistrarSocioActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        cardVisitanteRegistro.setOnClickListener {
            val intent = Intent(this, RegistrarVisitanteActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        btnContinuarRegistroDialog.setOnClickListener {
            Toast.makeText(this, "Seleccione Socio o Visitante para continuar", Toast.LENGTH_SHORT).show()
        }

        txtCerrarRegistro.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarDialogBusqueda() {
        val vista = LayoutInflater.from(this).inflate(R.layout.dialog_tipo_busqueda, null)
        val dialog = AlertDialog.Builder(this).setView(vista).create()

        val txtCerrarBusquedaDialog: TextView = vista.findViewById(R.id.txtCerrarBusquedaDialog)
        val cardSocioBusqueda: LinearLayout = vista.findViewById(R.id.cardSocioBusqueda)
        val cardVisitanteBusqueda: LinearLayout = vista.findViewById(R.id.cardVisitanteBusqueda)
        val btnContinuarBusquedaDialog: Button = vista.findViewById(R.id.btnContinuarBusquedaDialog)

        cardSocioBusqueda.setOnClickListener {
            val intent = Intent(this, ListaSociosActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        cardVisitanteBusqueda.setOnClickListener {
            val intent = Intent(this, ListaVisitantesActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        btnContinuarBusquedaDialog.setOnClickListener {
            Toast.makeText(this, "Seleccione Socio o Visitante para continuar", Toast.LENGTH_SHORT).show()
        }

        txtCerrarBusquedaDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}