package com.example.testugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testugd1.api.AkunApi
import com.example.testugd1.databinding.FragmentProfileBinding
import com.example.testugd1.models.Akun
import com.example.testugd1.room.Constant
import com.example.testugd1.room.UserDB
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class profileFragment() : Fragment() {

    val db by lazy {activity?.let { UserDB(it) }}
    private val key = "idKey"
    private val mypref= "myPref"
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    var sharedPreferences : SharedPreferences? = null
    private var queue: RequestQueue? = null
    //private var userLogin= akunLogin
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(requireContext())
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val sp = requireActivity().getSharedPreferences(mypref, 0)
        val id : Int = sp.getInt(key, 0)
        sharedPreferences = activity?.getSharedPreferences(mypref, Context.MODE_PRIVATE)

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, AkunApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val user: Akun = gson.fromJson(response,Akun::class.java)
//                var userList: Array<Akun> = gson.fromJson(response,Array<Akun>::class.java)
//                val user = gson.fromJson(response, Akun::class.java)
                binding.textViewNama.setText(user.username)
                binding.textViewUsername.setText(user.username)
                binding.textViewEmail.setText(user.email)
                binding.textViewBirtdate.setText(user.tanggalLahir)
                binding.textViewPhone.setText(user.noTelpon)



                Toast.makeText(requireContext(),"Data berhasil diambil!", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener { error ->

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireContext(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
//        CoroutineScope(Dispatchers.IO).launch {
//            val id = sharedPreferences!!.getString(key,"")!!.toInt()
//            val user = db?.userDao()?.getUser(id)?.get(0)
//            binding.textViewNama.setText(user?.username)
//            binding.textViewUsername.setText(user?.username)
//            binding.textViewEmail.setText(user?.email)
//            binding.textViewBirtdate.setText(user?.tanggalLahir)
//            binding.textViewPhone.setText(user?.noTelpon)
//        }

        binding.buttonEdit.setOnClickListener{
            val moveEdit = Intent(activity, EditProfile::class.java)
            startActivity(moveEdit)
            activity?.finish()
        }
        binding.buttonLogout.setOnClickListener{
            val moveEdit = Intent(activity, MainActivity::class.java)
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