package com.example.clubdeportivo_grupo5_comisionb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CobroAdapter(
    private val listaCobros: MutableList<CobroItem>,
    private val alHacerClickCobrar: (CobroItem) -> Unit
) : RecyclerView.Adapter<CobroAdapter.CobroViewHolder>() {

    class CobroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreItemCobro: TextView = itemView.findViewById(R.id.txtNombreItemCobro)
        val txtTipoItemCobro: TextView = itemView.findViewById(R.id.txtTipoItemCobro)
        val txtDetalleItemCobro: TextView = itemView.findViewById(R.id.txtDetalleItemCobro)
        val txtConceptoItemCobro: TextView = itemView.findViewById(R.id.txtConceptoItemCobro)
        val btnCobrarItemCobro: Button = itemView.findViewById(R.id.btnCobrarItemCobro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CobroViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cobro, parent, false)

        return CobroViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CobroViewHolder, position: Int) {
        val cobro = listaCobros[position]

        holder.txtNombreItemCobro.text = cobro.nombreCliente

        holder.txtTipoItemCobro.text = if (cobro.tipoCliente == "SOCIO") {
            "Socio N° ${cobro.clienteId.toString().padStart(5, '0')} - DNI: ${cobro.dniCliente}"
        } else {
            "Visitante - DNI: ${cobro.dniCliente}"
        }

        holder.txtDetalleItemCobro.text = cobro.detalle
        holder.txtConceptoItemCobro.text = "${cobro.concepto} - Monto: $ ${cobro.monto.toInt()}"

        holder.btnCobrarItemCobro.text = if (cobro.tipoCliente == "SOCIO") {
            "Cobrar cuota"
        } else {
            "Cobrar actividad"
        }

        holder.btnCobrarItemCobro.setOnClickListener {
            alHacerClickCobrar(cobro)
        }
    }

    override fun getItemCount(): Int {
        return listaCobros.size
    }

    fun actualizarLista(nuevaLista: MutableList<CobroItem>) {
        listaCobros.clear()
        listaCobros.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}