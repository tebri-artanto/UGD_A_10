package com.example.testugd1

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testugd1.databinding.FragmentHomeBinding
import com.example.testugd1.roomTP.Constant
import com.example.testugd1.roomTP.Pariwisata
import com.example.testugd1.roomTP.PariwisataDB
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    val db by lazy { activity?.let { PariwisataDB(it) } }
    lateinit var pariwisataAdapter: PariwisataAdapter2
    private val idKey = "idKey"
    private val mypref = "myPref"

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupRecyclerView()
        loadData()

//        val adapter : rvTempatPariwisata = rvTempatPariwisata(TempatPariwisata.litsOfKelas)
//        val rvPariwisata : RecyclerView = view.findViewById(R.id.rv_tempat_pariwisata)
//        rvPariwisata.layoutManager = layoutManager
//        rvPariwisata.adapter = adapter
    }



    private fun setupRecyclerView() {
        pariwisataAdapter = PariwisataAdapter2(arrayListOf(), object :
            PariwisataAdapter2.OnAdapterListener {
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
            layoutManager = LinearLayoutManager(activity)
            adapter = pariwisataAdapter
        }
    }



        private fun deleteDialog(pariwisata: Pariwisata) {
            val alertDialog = AlertDialog.Builder(activity as HomeActivity)
                alertDialog.apply {
                    setTitle("Confirmation")
                    setMessage("Are You Sure to delete this data From${pariwisata.nama}?")
                    setNegativeButton("Cancel", DialogInterface.OnClickListener
                    { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    setPositiveButton("Delete", DialogInterface.OnClickListener
                    { dialogInterface, i ->
                        dialogInterface.dismiss()
                        CoroutineScope(Dispatchers.IO).launch {
                            db?.pariwisataDao()?.deletePariwisata(pariwisata)
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
            sharedPreferences = activity?.getSharedPreferences(mypref, Context.MODE_PRIVATE)
            val id = sharedPreferences!!.getString(idKey, "")!!.toInt()
                CoroutineScope(Dispatchers.IO).launch {
                    val pariwisata = db?.pariwisataDao()?.getPariwisata(id)
                    Log.d("","dbResponse: $pariwisata")
                    withContext(Dispatchers.Main){
                        if (pariwisata != null) {
                            pariwisataAdapter.setData(pariwisata)
                        }
                    }
                }
        }

        fun setupListener() {
            binding.buttonCreate.setOnClickListener{
                intentEdit(0,Constant.TYPE_CREATE)
            }
        }

        fun intentEdit(pariwisataId: Int, intentType: Int) {
            startActivity(Intent(activity, EditActivity::class.java)
                    .putExtra("intent_id", pariwisataId)
                    .putExtra("intent_type", intentType)
            )
        }

}