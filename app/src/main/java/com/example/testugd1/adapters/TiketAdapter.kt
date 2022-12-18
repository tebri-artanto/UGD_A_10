package com.example.testugd1.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.testugd1.TiketMain
import com.example.testugd1.R
import com.example.testugd1.activity_beli_tiket
import com.example.testugd1.models.Tiket
import java.util.*
import kotlin.collections.ArrayList

class TiketAdapter (private var TiketList: List<Tiket>, context: Context) :
    RecyclerView.Adapter<TiketAdapter.ViewHolder>(), Filterable {

    private var filteredTiketList: MutableList<Tiket>
    private val context: Context

    init {
        filteredTiketList = ArrayList(TiketList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tiket, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredTiketList.size
    }

    fun setTiketList(TIketList: Array<Tiket>) {
        this.TiketList = TiketList.toList()
        filteredTiketList = TiketList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Tiket = filteredTiketList[position]
        holder.tvNama.text = Tiket.nama
        holder.tvJumlah.text = Tiket.jumlah
        holder.tvWisata.text = Tiket.wisata
        holder.tvPembayaran.text = Tiket.pembayaran

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data tiket ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is TiketMain) Tiket.id?.let { it1 ->
                        context.deleteTiket(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvTiket.setOnClickListener {
            val i = Intent(context, activity_beli_tiket::class.java)
            i.putExtra("id", Tiket.id)
            if (context is TiketMain)
                context.startActivityForResult(i, TiketMain.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Tiket> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(TiketList)
                } else {
                    for (Tiket in TiketList) {
                        if (Tiket.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(Tiket)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredTiketList.clear()
                filteredTiketList.addAll((filterResults.values as List<Tiket>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama : TextView
        var tvJumlah : TextView
        var tvWisata : TextView
        var tvPembayaran : TextView
        var btnDelete : ImageButton
        var cvTiket : CardView

        init {
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvJumlah = itemView.findViewById(R.id.tv_jumlah)
            tvWisata = itemView.findViewById(R.id.tv_wisata)
            tvPembayaran = itemView.findViewById(R.id.tv_pembayaran)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvTiket = itemView.findViewById(R.id.cv_tiket)
        }
    }
}