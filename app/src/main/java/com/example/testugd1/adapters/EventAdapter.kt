package com.example.testugd1.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.*
import com.example.testugd1.models.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(private var eventList: List<Event>, context: Context) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>(), Filterable {

    private var filteredEventList: MutableList<Event>
    private val context: Context

    init {
        filteredEventList = ArrayList(eventList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_event, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredEventList.size
    }

    fun setEventList(eventList: Array<Event>) {
        this.eventList = eventList.toList()
        filteredEventList = eventList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = filteredEventList[position]
        holder.tvNama.text = event.nama
        holder.tvLokasi.text = event.lokasi
        holder.tvHarga.text = event.harga
        holder.tvTipe.text = event.kategori

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data event ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is EventActivity) event.id?.let { it1 ->
                        context.deleteEvent(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvEvent.setOnClickListener {
            val i = Intent(context, AddEditEvent::class.java)
            i.putExtra("id", event.id)
            if (context is EventActivity)
                context.startActivityForResult(i, EventActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Event> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(eventList)
                } else {
                    for (event in eventList) {
                        if (event.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(event)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredEventList.clear()
                filteredEventList.addAll((filterResults.values as List<Event>))
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
        var cvEvent : CardView

        init {
            tvNama = itemView.findViewById(R.id.tv_namaEvent)
            tvLokasi = itemView.findViewById(R.id.tv_lokasiEvent)
            tvTipe = itemView.findViewById(R.id.tv_kategori)
            tvHarga = itemView.findViewById(R.id.tv_hargaEvent)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvEvent = itemView.findViewById(R.id.cv_event)
        }
    }
}