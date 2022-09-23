package com.example.testugd1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.testugd1.room.User
import kotlinx.android.synthetic.main.activity_profile_adapter.view.*
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList

class ProfileAdapter (private val users: ArrayList<User>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<ProfileAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_profile_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val user = users[position]
//        holder.view.textView_username.text = user.username
//        holder.view.textView_email.text = user.email
//        holder.view.textView_birtdate.text = user.tanggalLahir
//        holder.view.textView_phone.text = user.noTelpon

    }

    override fun getItemCount() = users.size

    inner class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<User>) {
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(note: User)
        fun onUpdate(note: User)
        fun onDelete(note: User)
    }


}