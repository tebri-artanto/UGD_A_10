package com.example.testugd1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testugd1.databinding.ActivityEditBinding
import com.example.testugd1.roomTP.PariwisataDB
import com.example.testugd1.roomTP.Constant
import com.example.testugd1.roomTP.Pariwisata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : Fragment() {

//    val db by lazy { PariwisataDB(this) }
    val db by lazy { activity?.let { PariwisataDB(it) } }

    private var noteId: Int = 0
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: ActivityEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ActivityEditBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
//        val idUser = sharedPreferences!!.getString(id,"")!!.toInt()
//
//

    }

//    fun setupView(){
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val intentType = intent.getIntExtra("intent_type", 0)
//        when(intentType){
//            Constant.TYPE_CREATE -> {
//                binding.buttonUpdate.visibility = View.GONE
//            }
//            Constant.TYPE_READ -> {
//                binding.buttonSave.visibility = View.GONE
//                binding.buttonUpdate.visibility = View.GONE
//                getNote()
//            }
//            Constant.TYPE_UPDATE -> {
//                binding.buttonSave.visibility = View.GONE
//                getNote()
//            }
//        }
//    }
//
//    private fun setupListener() {
//        binding.buttonSave.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.pariwisataDao().addPariwisata(
//                    Pariwisata(0, binding.editNama.text.toString(), binding.editLokasi.text.toString(),
//                        binding.editHarga.text.toString(), binding.editTipe.text.toString())
//                )
//                finish()
//            }
//        }
//
//        binding.buttonUpdate.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.pariwisataDao().updatePariwisata(
//                    Pariwisata(noteId, binding.editNama.text.toString(), binding.editLokasi.text.toString(),
//                        binding.editHarga.text.toString(), binding.editTipe.text.toString())
//                )
//                finish()
//            }
//        }
//    }
//
//    fun getNote(){
//        noteId = intent.getIntExtra("intent_id", 0)
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db.pariwisataDao().getPariwisata(noteId)[0]
//            binding.editNama.setText(notes.nama)
//            binding.editLokasi.setText(notes.lokasi)
//            binding.editHarga.setText(notes.harga)
//            binding.editTipe.setText(notes.tipe)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return super.onSupportNavigateUp()
//    }
//
//    val db by lazy { PariwisataDB(this) }
//    private var noteId: Int = 0
//
//    private lateinit var binding : ActivityEditBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityEditBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setupView()
//        setupListener()
//    }
//
//    fun setupView(){
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val intentType = intent.getIntExtra("intent_type", 0)
//        when(intentType){
//            Constant.TYPE_CREATE -> {
//                binding.buttonUpdate.visibility = View.GONE
//            }
//            Constant.TYPE_READ -> {
//                binding.buttonSave.visibility = View.GONE
//                binding.buttonUpdate.visibility = View.GONE
//                getNote()
//            }
//            Constant.TYPE_UPDATE -> {
//                binding.buttonSave.visibility = View.GONE
//                getNote()
//            }
//        }
//    }
//
//    private fun setupListener() {
//        binding.buttonSave.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.pariwisataDao().addPariwisata(
//                    Pariwisata(0, binding.editTitle.text.toString(), binding.editNote.text.toString())
//                )
//                finish()
//            }
//        }
//
//        binding.buttonUpdate.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.pariwisataDao().updatePariwisata(
//                    Pariwisata(noteId, binding.editTitle.text.toString(), binding.editNote.text.toString())
//                )
//                finish()
//            }
//        }
//    }
//
//    fun getNote(){
//        noteId = intent.getIntExtra("intent_id", 0)
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db.pariwisataDao().getPariwisata(noteId)[0]
//            binding.editTitle.setText(notes.)
//            binding.editNote.setText(notes.)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return super.onSupportNavigateUp()
//    }

}