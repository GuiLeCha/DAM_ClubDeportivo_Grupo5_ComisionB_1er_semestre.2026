package com.example.clubdeportivo_grupo5_comisionb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VisitanteAdapter(
    private val listaVisitantes: MutableList<Visitante>,
    private val alHacerClickPerfil: (Visitante) -> Unit,
    private val alHacerClickBaja: (Visitante) -> Unit
) : RecyclerView.Adapter<VisitanteAdapter.VisitanteViewHolder>() {

    class VisitanteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtInicialesItemVisitante: TextView = itemView.findViewById(R.id.txtInicialesItemVisitante)
        val txtNombreItemVisitante: TextView = itemView.findViewById(R.id.txtNombreItemVisitante)
        val txtDniItemVisitante: TextView = itemView.findViewById(R.id.txtDniItemVisitante)
        val txtActividadItemVisitante: TextView = itemView.findViewById(R.id.txtActividadItemVisitante)
        val txtContactoItemVisitante: TextView = itemView.findViewById(R.id.txtContactoItemVisitante)
        val btnPerfilItemVisitante: Button = itemView.findViewById(R.id.btnPerfilItemVisitante)
        val btnBajaItemVisitante: Button = itemView.findViewById(R.id.btnBajaItemVisitante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitanteViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visitante, parent, false)

        return VisitanteViewHolder(vista)
    }

    override fun onBindViewHolder(holder: VisitanteViewHolder, position: Int) {
        val visitante = listaVisitantes[position]

        holder.txtInicialesItemVisitante.text = obtenerIniciales(visitante.nombre, visitante.apellido)
        holder.txtNombreItemVisitante.text = visitante.nombreCompleto
        holder.txtDniItemVisitante.text = "Visitante - DNI: ${visitante.dni}"
        holder.txtActividadItemVisitante.text = "Actividad: ${visitante.actividad}"
        holder.txtContactoItemVisitante.text = "Contacto: ${obtenerContactoVisitante(visitante)}"

        holder.btnPerfilItemVisitante.setOnClickListener {
            alHacerClickPerfil(visitante)
        }

        holder.btnBajaItemVisitante.setOnClickListener {
            alHacerClickBaja(visitante)
        }
    }

    override fun getItemCount(): Int {
        return listaVisitantes.size
    }

    fun actualizarLista(nuevaLista: MutableList<Visitante>) {
        listaVisitantes.clear()
        listaVisitantes.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    private fun obtenerContactoVisitante(visitante: Visitante): String {
        return when {
            visitante.telefono.isNotEmpty() && visitante.email.isNotEmpty() -> "${visitante.telefono} / ${visitante.email}"
            visitante.telefono.isNotEmpty() -> visitante.telefono
            visitante.email.isNotEmpty() -> visitante.email
            else -> "Sin contacto"
        }
    }

    private fun obtenerIniciales(nombre: String, apellido: String): String {
        val inicialNombre = nombre.firstOrNull()?.uppercaseChar() ?: 'V'
        val inicialApellido = apellido.firstOrNull()?.uppercaseChar() ?: 'C'
        return "$inicialNombre$inicialApellido"
    }
}