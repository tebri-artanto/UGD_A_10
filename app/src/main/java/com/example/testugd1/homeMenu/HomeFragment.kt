package com.example.testugd1.homeMenu

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testugd1.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.testugd1.EditActivity
import com.example.testugd1.room.Constant
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import com.example.testugd1.roomTP.Pariwisata
import com.example.testugd1.roomTP.PariwisataDB
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class homeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        //val adapter : rvTempatPariwisata = rvTempatPariwisata(TempatPariwisata.litsOfKelas)
//        val rvPariwisata : RecyclerView = view.findViewById(R.id.rv_tempat_pariwisata)
//        rvPariwisata.layoutManager = layoutManager
//        rvPariwisata.adapter = adapter
    }}
//    val db by lazy { PariwisataDB(this) }
//    lateinit var noteAdapter: NoteAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
////        setupListener()
////        setupRecycleView()
//    }
//}

//    private fun setupRecycleView() {
//        noteAdapter = NoteAdapter(arrayListOf(), object :
//            NoteAdapter.OnAdapterListener{
//            override fun onClick(pariwisata: Pariwisata) {
//                intentEdit(pariwisata.id, Constant.TYPE_READ)
//            }
//
//            override fun onUpdate(pariwisata: Pariwisata) {
//                intentEdit(pariwisata.id, Constant.TYPE_UPDATE)
//            }
//
//            override fun onDelete(pariwisata: Pariwisata) {
//                deleteDialog(pariwisata)
//            }
//        })
//        list_note.apply {
//            layoutManager = LinearLayoutManager(applicationContext)
//            adapter = noteAdapter
//        }
//    }
//
//    private fun deleteDialog(pariwisata: Pariwisata){
//        val alertDialog = AlertDialog.Builder(this)
//        alertDialog.apply {
//            setTitle("Confirmation")
//            setMessage("Are You Sure to delete this data From${user.username}?")
//            setNegativeButton("Cancel", DialogInterface.OnClickListener
//            { dialogInterface, i ->
//                dialogInterface.dismiss()
//            })
//            setPositiveButton("Delete", DialogInterface.OnClickListener
//            { dialogInterface, i ->
//                dialogInterface.dismiss()
//                CoroutineScope(Dispatchers.IO).launch {
//                    db.pariwisataDao().deleteUser(user)
//                    loadData()
//                }
//            })
//        }
//        alertDialog.show()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        loadData()
//    }
//
//    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db.userDao().getUser(User)
//            Log.d("MainActivity","dbResponse: $notes")
//            withContext(Dispatchers.Main){
//                noteAdapter.setData(notes)
//            }
//        }
//    }
//
//    fun setupListener() {
//        button_create.setOnClickListener{
//            intentEdit(0,Constant.TYPE_CREATE)
//        }
//    }
//
//    fun intentEdit(noteId : Int, intentType: Int) {
//        startActivity(
//            Intent(applicationContext, EditActivity::class.java)
//                .putExtra("intent_id", noteId)
//                .putExtra("intent_type", intentType)
//        )
//    }
//}