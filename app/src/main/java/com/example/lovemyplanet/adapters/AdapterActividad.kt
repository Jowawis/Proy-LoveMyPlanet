package com.example.lovemyplanet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lovemyplanet.Actividad
import com.example.lovemyplanet.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class AdapterActividad(private var options: FirestoreRecyclerOptions<Actividad>, private val itemClickListener: OnActividadClickListener)
    : FirestoreRecyclerAdapter<Actividad,AdapterActividad.ViewHolder>(options) {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvDescrip: TextView = itemView.findViewById(R.id.tvDescrip)
        val tvPuntos: TextView = itemView.findViewById(R.id.tvPuntos)
        val image : ImageView = itemView.findViewById(R.id.image)
//        val tvImage: ImageView = itemView.findViewById(R.id.image)

    }

    interface OnActividadClickListener {
        fun onActividadClick(actividad: Actividad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_actividad,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, actividad: Actividad) {
        holder.tvNombre.text = actividad.nombre
        holder.tvDescrip.text = actividad.descripcion
        holder.tvPuntos.text = actividad.puntos.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.onActividadClick(actividad)
        }
        Glide
            .with(holder.itemView)
            .load(actividad.urlImage)
            .centerCrop()
            .into(holder.image)
       // holder.image.setImageResource(divisa.image)
    }





}