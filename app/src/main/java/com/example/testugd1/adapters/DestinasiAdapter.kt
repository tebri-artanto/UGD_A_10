package com.example.gd11_a_0680.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.testugd1.AddEditDestinasi
import com.example.testugd1.DestinasiMain
import com.example.testugd1.R
import com.example.gd11_a_0680.models.Destinasi
import java.util.*
import kotlin.collections.ArrayList

class DestinasiAdapter (private var destinasiList: List<Destinasi>, context: Context) :
    RecyclerView.Adapter<DestinasiAdapter.ViewHolder>(), Filterable {

    private var filteredDestinasiList: MutableList<Destinasi>
    private val context: Context

    init {
        filteredDestinasiList = ArrayList(destinasiList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_destinasi, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredDestinasiList.size
    }

    fun setDestinasiList(destinasiList: Array<Destinasi>) {
        this.destinasiList = destinasiList.toList()
        filteredDestinasiList = destinasiList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destinasi = filteredDestinasiList[position]
        holder.tvNama.text = destinasi.nama
        holder.tvLokasi.text = destinasi.lokasi
        holder.tvHarga.text = destinasi.harga
        holder.tvTipe.text = destinasi.tipe

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data destinasi ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is DestinasiMain) destinasi.id?.let { it1 ->
                        context.deleteDestinasi(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvDestinasi.setOnClickListener {
            val i = Intent(context, AddEditDestinasi::class.java)
            i.putExtra("id", destinasi.id)
            if (context is DestinasiMain)
                context.startActivityForResult(i, DestinasiMain.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Destinasi> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(destinasiList)
                } else {
                    for (destinasi in destinasiList) {
                        if (destinasi.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(destinasi)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredDestinasiList.clear()
                filteredDestinasiList.addAll((filterResults.values as List<Destinasi>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama : TextView
        var tvLokasi : TextView
        var tvHarga : TextView
        var tvTipe : TextView
        var btnDelete : ImageButton
        var cvDestinasi : CardView

        init {
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvLokasi = itemView.findViewById(R.id.tv_npm)
            tvTipe = itemView.findViewById(R.id.tv_prodi)
            tvHarga = itemView.findViewById(R.id.tv_fakultas)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvDestinasi = itemView.findViewById(R.id.cv_destinasi)
        }
    }
}