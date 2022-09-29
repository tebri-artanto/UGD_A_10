package com.example.testugd1.homeMenu

import android.content.Context
import android.os.Bundle
<<<<<<< Updated upstream
=======
import android.content.Intent
>>>>>>> Stashed changes
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testugd1.databinding.FragmentHomeBinding
import com.example.testugd1.room.Constant
import com.example.testugd1.roomTP.Pariwisata
import com.example.testugd1.roomTP.PariwisataDB
import com.example.testugd1.roomTP.PariwisataAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class homeFragment : Fragment() {

    val db by lazy { activity?.let { PariwisataDB(it) } }
    lateinit var pariwisataAdapter: PariwisataAdapter
    private val id = "idKey"
    private val mypref= "myPref"

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var sharedPreferences : SharedPreferences? = null
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
//        setupListener()
//        setupRecyclerView()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        sharedPreferences = activity?.getSharedPreferences(mypref, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(id,"")!!.toInt()
//        val adapter : rvTempatPariwisata = rvTempatPariwisata(TempatPariwisata.litsOfKelas)
//        val rvPariwisata : RecyclerView = view.findViewById(R.id.rv_tempat_pariwisata)
//        rvPariwisata.layoutManager = layoutManager
//        rvPariwisata.adapter = adapter
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_home)
//        setupListener()
//        setupRecycleView()
//    }

    private fun setupRecyclerView() {
        pariwisataAdapter= PariwisataAdapter(arrayListOf(), object :
            PariwisataAdapter.OnAdapterListener{
            override fun onClick(pariwisata: Pariwisata) {
                intentEdit(pariwisata.id, Constant.TYPE_READ)
            }

            override fun onUpdate(pariwisata: Pariwisata) {
                intentEdit(pariwisata.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(pariwisata: Pariwisata) {
                deleteDialog(pariwisata)
            }
        })
        list_note.apply {
//            layoutManager = LinearLayoutManager(applicationContex)
            adapter = pariwisataAdapter
        }
    }

    private fun deleteDialog(pariwisata: Pariwisata){
        //val alertDialog = AlertDialog.Builder(this)
//        alertDialog.apply {
//            setTitle("Confirmation")
//            setMessage("Are You Sure to delete this data From${pariwisata.nama}?")
//            setNegativeButton("Cancel", DialogInterface.OnClickListener
//            { dialogInterface, i ->
//                dialogInterface.dismiss()
//            })
//            setPositiveButton("Delete", DialogInterface.OnClickListener
//            { dialogInterface, i ->
//                dialogInterface.dismiss()
//                CoroutineScope(Dispatchers.IO).launch {
//                    db.pariwisataDao().deletePariwisata(pariwisata)
//                    loadData()
//                }
//            })
//        }
//        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val pariwisata = db.pariwisataDao().getPariwisata()
//            Log.d("MainActivity","dbResponse: $pariwisata")
//            withContext(Dispatchers.Main){
//                pariwisataAdapter.setData(pariwisata)
//            }
//        }
    }

    fun setupListener() {
        button_create.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(noteId : Int, intentType: Int) {
        startActivity(
            Intent(applicationCo, EditActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }
}