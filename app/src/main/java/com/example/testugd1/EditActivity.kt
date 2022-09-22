package com.example.testugd1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testugd1.databinding.ActivityEditBinding
import com.example.testugd1.roomTP.PariwisataDB
import com.example.testugd1.roomTP.Constant
import com.example.testugd1.roomTP.Pariwisata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { PariwisataDB(this) }
    private var noteId: Int = 0

    private lateinit var binding : ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                binding.buttonUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getNote()
            }
        }
    }

    private fun setupListener() {
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.pariwisataDao().addPariwisata(
                    Pariwisata(0, binding.editTitle.text.toString(), binding.editNote.text.toString())
                )
                finish()
            }
        }

        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.pariwisataDao().updatePariwisata(
                    Pariwisata(noteId, binding.editTitle.text.toString(), binding.editNote.text.toString())
                )
                finish()
            }
        }
    }

    fun getNote(){
        noteId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.pariwisataDao().getPariwisata(noteId)[0]
            binding.editTitle.setText(notes.)
            binding.editNote.setText(notes.)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}