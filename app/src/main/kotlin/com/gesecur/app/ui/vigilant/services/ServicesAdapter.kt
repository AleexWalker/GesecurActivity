package com.gesecur.app.ui.vigilant.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gesecur.app.R

class ServicesAdapter (private val workList: ArrayList<ServicesCard>,
private val clickListener: (ServicesCard) -> Unit): RecyclerView.Adapter<ServicesAdapter.MyViewHolder>(){

    class MyViewHolder (itemView: View, clickAtPosition: (String) -> Unit): RecyclerView.ViewHolder(itemView){
        val contrato: TextView = itemView.findViewById(R.id.tv_identifierx)
        val servicio: TextView = itemView.findViewById(R.id.tv_descripcionx)
        val localizacion: TextView = itemView.findViewById(R.id.tv_hourx)
        val horas: TextView = itemView.findViewById(R.id.tv_locationx)
        val fechas: TextView = itemView.findViewById(R.id.tv_datex)
        var cuadrante = String()
        var vigilante_id = String()

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.work_order_item_experimental, parent, false)) {
            clickListener(workList[it.toInt()])
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = workList[position]
        holder.contrato.text = currentItem.contrato
        holder.servicio.text = currentItem.servicio
        holder.localizacion.text = currentItem.localizacion
        holder.horas.text = currentItem.horas
        holder.fechas.text = currentItem.fechaAbajo
        holder.cuadrante = currentItem.cuadrante
        holder.vigilante_id = currentItem.vigilante_id

        holder.itemView.setOnClickListener {
            clickListener(workList[position])
        }
    }

    override fun getItemCount(): Int {
        return workList.size
    }

}
