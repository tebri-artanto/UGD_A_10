package com.example.testugd1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testugd1.Entity.TempatPariwisata
import com.example.testugd1.room.UserDB
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class profileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var userDB: UserDB
    lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
        setupRecyclerView()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val layoutManager = LinearLayoutManager(context)
//    }

    private fun setupRecyclerView() {
//        profileAdapter = ProfileAdapter(arrayListOf(), object :
//            ProfileAdapter.OnAdapterListener{
////            override fun onClick(note: Note) {
////                Toast.makeText(applicationContext, note.title, Toast.LENGTH_SHORT).show()
////                intentEdit(note.id,Constant.TYPE_READ)
////            }
////            override fun onUpdate(note: Note) {
////                intentEdit(note.id, Constant.TYPE_UPDATE)
////            }
////            override fun onDelete(note: Note) {
////                deleteDialog(note)
////            }
//        })
        user_p      arofile.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = profileAdapter
        }
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }
    //untuk load data yang tersimpan pada database yang sudah create data

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = userDB.userDao().getUsers()
            Log.d("profileFragment","dbResponse: $users")
            withContext(Dispatchers.Main){
                profileAdapter.setData( users )
            }
        }
    }



}