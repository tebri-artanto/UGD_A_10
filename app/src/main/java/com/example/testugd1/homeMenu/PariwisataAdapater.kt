package com.example.testugd1.homeMenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.R
import com.example.testugd1.room.User
import kotlinx.android.synthetic.main.activity_pariwisata_adapter.view.*


class NoteAdapter(private val User: ArrayList<User>, private val
listener: OnAdapterListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {
        return NoteViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.activity_pariwisata_adapter,parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = User[position]
        holder.view.text_title.text = note.username
        holder.view.text_title.setOnClickListener{
            listener.onClick(note)
        }
        holder.view.icon_pesan.setOnClickListener{
            listener.onUpdate(note)
        }
        holder.view.icon_profil.setOnClickListener{
            listener.onDelete(note)
        }

    }

    override fun getItemCount() = User.size

    inner class NoteViewHolder( val view: View) :
        RecyclerView.ViewHolder(view)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<User>){
        User.clear()
        User.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(note: User)
        fun onUpdate(note: User)
        fun onDelete(note: User)
    }
}