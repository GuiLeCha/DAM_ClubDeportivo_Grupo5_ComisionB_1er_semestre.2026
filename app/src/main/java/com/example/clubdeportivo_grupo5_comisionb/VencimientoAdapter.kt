package com.example.clubdeportivo_grupo5_comisionb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VencimientoAdapter(
    private val listaSocios: MutableList<Socio>,
    private val alHacerClickCobrar: (Socio) -> Unit,
    private val alHacerClickNotificar: (Socio) -> Unit
) : RecyclerView.Adapter<VencimientoAdapter.VencimientoViewHolder>() {

    class VencimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreItemVencimiento: TextView = itemView.findViewById(R.id.txtNombreItemVencimiento)
        val txtNumeroItemVencimiento: TextView = itemView.findViewById(R.id.txtNumeroItemVencimiento)
        val txtCuotaItemVencimiento: TextView = itemView.findViewById(R.id.txtCuotaItemVencimiento)
        val txtEstadoItemVencimiento: TextView = itemView.findViewById(R.id.txtEstadoItemVencimiento)
        val txtContactoItemVencimiento: TextView = itemView.findViewById(R.id.txtContactoItemVencimiento)
        val btnNotificarItemVencimiento: Button = itemView.findViewById(R.id.btnNotificarItemVencimiento)
        val btnCobrarItemVencimiento: Button = itemView.findViewById(R.id.btnCobrarItemVencimiento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VencimientoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vencimiento, parent, false)

        return VencimientoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: VencimientoViewHolder, position: Int) {
        val socio = listaSocios[position]

        holder.txtNombreItemVencimiento.text = socio.nombreCompleto
        holder.txtNumeroItemVencimiento.text =
            "Socio N° ${socio.id.toString().padStart(5, '0')} - DNI: ${socio.dni}"
        holder.txtCuotaItemVencimiento.text = "Vencimiento cuota: ${socio.fechaVencimientoCuota}"
        holder.txtEstadoItemVencimiento.text = "Estado: Vence hoy"
        holder.txtContactoItemVencimiento.text = "Contacto: ${obtenerContactoSocio(socio)}"

        holder.btnNotificarItemVencimiento.setOnClickListener {
            alHacerClickNotificar(socio)
        }

        holder.btnCobrarItemVencimiento.setOnClickListener {
            alHacerClickCobrar(socio)
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
}