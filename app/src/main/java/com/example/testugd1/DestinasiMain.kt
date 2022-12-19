package com.example.testugd1


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testugd1.api.DestinasiApi
import com.example.testugd1.models.Destinasi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.example.testugd1.adapters.DestinasiAdapter
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class DestinasiMain : AppCompatActivity() {
    private var srDestinasi: SwipeRefreshLayout? = null
    private var adapter: DestinasiAdapter? = null
    private var svDestinasi: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinasi_main)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srDestinasi = findViewById(R.id.sr_destinasi)
        svDestinasi = findViewById(R.id.sv_destinasi)

        srDestinasi?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allDestinasi()  })
        svDestinasi?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter!!.filter.filter(p0)
                return false
            }
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val i = Intent(this@DestinasiMain, AddEditDestinasi::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }
        val fabback = findViewById<FloatingActionButton>(R.id.fab_back)
        fabback.setOnClickListener {
            val moveHome = Intent(this, HomeActivity::class.java)
            startActivity(moveHome)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_destinasi)
        adapter = DestinasiAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allDestinasi()
    }

    private fun allDestinasi() {
        srDestinasi!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, DestinasiApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                var destinasi : Array<Destinasi> = gson.fromJson(response, Array<Destinasi>::class.java)

                adapter!!.setDestinasiList(destinasi)
                adapter!!.filter.filter(svDestinasi!!.query)
                srDestinasi!!.isRefreshing = false

                if(!destinasi.isEmpty())
                    Toast.makeText(this@DestinasiMain, "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@DestinasiMain, "Data Kosong!", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                srDestinasi!!.isRefreshing = false
                try{
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@DestinasiMain,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@DestinasiMain, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    fun deleteDestinasi(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, DestinasiApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var destinasi = gson.fromJson(response, Destinasi::class.java)
                if(destinasi != null)
                    Toast.makeText(this@DestinasiMain, "Data Berhasil Dihapus!", Toast.LENGTH_SHORT).show()
                allDestinasi()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@DestinasiMain,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@DestinasiMain, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap<String, String>()
                headers ["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allDestinasi()
    }

    // Fungsi ini digunakan menampilkan layout loading
    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.GONE
        }
    }
}