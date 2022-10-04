package com.example.testugd1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.roomTP.Pariwisata
import kotlinx.android.synthetic.main.activity_pariwisata_adapter.view.*

class PariwisataAdapter2(private val pariwisata: ArrayList<Pariwisata>, private val
        listener: OnAdapterListener) :
RecyclerView.Adapter<PariwisataAdapter2.PariwisataViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PariwisataViewHolder{
        return PariwisataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_pariwisata_adapter,parent, false)
        )
    }

    override fun onBindViewHolder(holder: PariwisataViewHolder, position: Int) {
        val pariwis = pariwisata[position]
        holder.view.text_title.text = pariwis.nama
        holder.view.text_title.setOnClickListener{
            listener.onClick(pariwis)
        }
        holder.view.icon_pesan.setOnClickListener{
            listener.onUpdate(pariwis)
        }
        holder.view.icon_profil.setOnClickListener{
            listener.onDelete(pariwis)
        }

    }

    override fun getItemCount() = pariwisata.size

    inner class PariwisataViewHolder( val view: View) :
        RecyclerView.ViewHolder(view)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Pariwisata>){
        pariwisata.clear()
        pariwisata.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(pariwisata: Pariwisata)
        fun onUpdate(pariwisata: Pariwisata)
        fun onDelete(pariwisata: Pariwisata)
    }
}