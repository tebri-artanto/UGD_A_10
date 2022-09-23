package com.example.testugd1.homeMenu

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testugd1.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.testugd1.EditActivity
import com.example.testugd1.room.Constant
import com.example.testugd1.room.User
import com.example.testugd1.room.UserDB
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class homeFragment : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecycleView()
    }

    private fun setupRecycleView() {
        noteAdapter = NoteAdapter(arrayListOf(), object :
            NoteAdapter.OnAdapterListener{
            override fun onClick(note: User) {
                intentEdit(note.id, Constant.TYPE_READ)
            }

            override fun onUpdate(note: User) {
                intentEdit(note.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(note: User) {
                deleteDialog(note)
            }
        })
        rv_tempat_pariwisata.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }

    private fun deleteDialog(user: User){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are You Sure to delete this data From${user.username}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener
            { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener
            { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().deleteUser(user)
                    loadData()
                }
            })
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.userDao().getUser(User)
            Log.d("MainActivity","dbResponse: $notes")
            withContext(Dispatchers.Main){
                noteAdapter.setData(notes)
            }
        }
    }

    fun setupListener() {
        button_create.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(noteId : Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }
}