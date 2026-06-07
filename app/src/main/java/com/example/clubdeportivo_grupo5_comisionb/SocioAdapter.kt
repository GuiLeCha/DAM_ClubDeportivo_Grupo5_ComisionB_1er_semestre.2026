package com.example.clubdeportivo_grupo5_comisionb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SocioAdapter(
    private val listaSocios: MutableList<Socio>,
    private val alHacerClickPerfil: (Socio) -> Unit,
    private val alHacerClickBaja: (Socio) -> Unit
) : RecyclerView.Adapter<SocioAdapter.SocioViewHolder>() {

    class SocioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtInicialesItemSocio: TextView = itemView.findViewById(R.id.txtInicialesItemSocio)
        val txtNombreItemSocio: TextView = itemView.findViewById(R.id.txtNombreItemSocio)
        val txtNumeroItemSocio: TextView = itemView.findViewById(R.id.txtNumeroItemSocio)
        val txtDetalleItemSocio: TextView = itemView.findViewById(R.id.txtDetalleItemSocio)
        val txtContactoItemSocio: TextView = itemView.findViewById(R.id.txtContactoItemSocio)
        val btnPerfilItemSocio: Button = itemView.findViewById(R.id.btnPerfilItemSocio)
        val btnBajaItemSocio: Button = itemView.findViewById(R.id.btnBajaItemSocio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocioViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_socio, parent, false)

        return SocioViewHolder(vista)
    }

    override fun onBindViewHolder(holder: SocioViewHolder, position: Int) {
        val socio = listaSocios[position]

        holder.txtInicialesItemSocio.text = obtenerIniciales(socio.nombre, socio.apellido)
        holder.txtNombreItemSocio.text = socio.nombreCompleto
        holder.txtNumeroItemSocio.text = "Socio N° ${socio.id.toString().padStart(5, '0')}"
        holder.txtDetalleItemSocio.text = "DNI: ${socio.dni} - Vencimiento cuota: ${socio.fechaVencimientoCuota}"
        holder.txtContactoItemSocio.text = "Contacto: ${obtenerContactoSocio(socio)}"

        holder.btnPerfilItemSocio.setOnClickListener {
            alHacerClickPerfil(socio)
        }

        holder.btnBajaItemSocio.setOnClickListener {
            alHacerClickBaja(socio)
        }
    }

    override fun getItemCount(): Int {
        return listaSocios.size
    }

    fun actualizarLista(nuevaLista: MutableList<Socio>) {
        listaSocios.clear()
        listaSocios.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    private fun obtenerContactoSocio(socio: Socio): String {
        return when {
            socio.telefono.isNotEmpty() && socio.email.isNotEmpty() -> "${socio.telefono} / ${socio.email}"
            socio.telefono.isNotEmpty() -> socio.telefono
            socio.email.isNotEmpty() -> socio.email
            else -> "Sin contacto"
        }
    }

    private fun obtenerIniciales(nombre: String, apellido: String): String {
        val inicialNombre = nombre.firstOrNull()?.uppercaseChar() ?: 'S'
        val inicialApellido = apellido.firstOrNull()?.uppercaseChar() ?: 'C'
        return "$inicialNombre$inicialApellido"
    }
}