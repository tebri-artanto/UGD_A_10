package com.example.testugd1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.Entity.TempatPariwisata

class rvTempatPariwisata(private val data: Array<TempatPariwisata>) : RecyclerView.Adapter<rvTempatPariwisata.viewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_pariwisata, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNamaTempat.text = currentItem.Nama_Tempat
        holder.tvKapasitas.text = currentItem.Kapasitas.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaTempat: TextView = itemView.findViewById(R.id.tv_Nama_Tempat)
        val tvKapasitas : TextView = itemView.findViewById(R.id.tv_Kapasitas)
    }
}