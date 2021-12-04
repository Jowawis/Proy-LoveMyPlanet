package com.example.lovemyplanet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lovemyplanet.R


class AdapterRecycler(private var titulos: List<String>, private val itemClickListener: OnTitleClickListener)
    : RecyclerView.Adapter<AdapterRecycler.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitulo: TextView = itemView.findViewById(R.id.titulo)
//        val tvImage: ImageView = itemView.findViewById(R.id.image)

    }

    interface OnTitleClickListener {
        fun onTitleClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitulo.text = titulos[position]
        holder.itemView.setOnClickListener {
            itemClickListener.onTitleClick(position)
        }
       // holder.image.setImageResource(divisa.image)
    }

    override fun getItemCount(): Int {
        return titulos.size
    }



}