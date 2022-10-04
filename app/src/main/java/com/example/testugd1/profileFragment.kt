package com.example.testugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testugd1.databinding.FragmentProfileBinding
import com.example.testugd1.room.Constant
import com.example.testugd1.room.UserDB
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class profileFragment : Fragment() {

    val db by lazy {activity?.let { UserDB(it) }}
    private val key = "idKey"
    private val mypref= "myPref"
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    var sharedPreferences : SharedPreferences? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        sharedPreferences = activity?.getSharedPreferences(mypref, Context.MODE_PRIVATE)

        CoroutineScope(Dispatchers.IO).launch {
            val id = sharedPreferences!!.getString(key,"")!!.toInt()
            val user = db?.userDao()?.getUser(id)?.get(0)
            binding.textViewNama.setText(user?.username)
            binding.textViewUsername.setText(user?.username)
            binding.textViewEmail.setText(user?.email)
            binding.textViewBirtdate.setText(user?.tanggalLahir)
            binding.textViewPhone.setText(user?.noTelpon)
        }
        binding.buttonEdit.setOnClickListener{
            val moveEdit = Intent(activity, EditProfile::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }

    }

    //    val db by lazy {activity?.let { UserDB(it) }}
//    private val id = "idKey"
//    private val mypref= "myPref"
//
//    private var _binding: FragmentProfileBinding? = null
//    private val binding get() = _binding!!
//    var sharedPreferences : SharedPreferences? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val layoutManager = LinearLayoutManager(context)
//        sharedPreferences = activity?.getSharedPreferences(mypref, Context.MODE_PRIVATE)
//        val id = sharedPreferences!!.getString(id,"")!!.toInt()
//
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val user = db?.userDao()?.getUser(id)?.get(0)
//                binding.textViewNama.setText(user?.username)
//                binding.textViewUsername.setText(user?.username)
//                binding.textViewEmail.setText(user?.email)
//                binding.textViewBirtdate.setText(user?.tanggalLahir)
//                binding.textViewPhone.setText(user?.noTelpon)
//
//        }


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
//        user_p      arofile.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = profileAdapter
//        }
//    }
//    override fun onStart() {
//        super.onStart()
//        loadData()
//    }
//    //untuk load data yang tersimpan pada database yang sudah create data
//
//    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val users = userDB.userDao().getUsers()
//            Log.d("profileFragment","dbResponse: $users")
//            withContext(Dispatchers.Main){
//                profileAdapter.setData( users )
//            }
//        }
//    }
//
//
    }
}