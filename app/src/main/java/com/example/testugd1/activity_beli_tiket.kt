package com.example.testugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testugd1.api.TiketApi
import com.example.testugd1.models.Tiket
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class activity_beli_tiket : AppCompatActivity() {
    companion object {
        private val TIPE_LIST = arrayOf("Kartu", "E-Money")
    }

    private var etNama: EditText? = null
    private var etJumlah: EditText? = null
    private var etWisata: EditText? = null
    private var etPembayaran: AutoCompleteTextView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beli_tiket)

        queue = Volley.newRequestQueue(this)
        etNama = findViewById(R.id.et_Nama)
        etJumlah = findViewById(R.id.et_Jumlah)
        etWisata = findViewById(R.id.et_Wisata)
        etPembayaran = findViewById(R.id.et_Pembayaran)
        layoutLoading = findViewById(R.id.layout_loading)

        setExposedDropDownMenu()

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.BeliTiket)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Tiket")
            btnSave.setOnClickListener { createTiket() }
        } else {
            tvTitle.setText("Edit Tiket")
            getTiketById(id)
            btnSave.setOnClickListener { updateTiket(id) }
        }
    }

    fun setExposedDropDownMenu() {
        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(this,
            R.layout.tipe_pembayaran_list, TIPE_LIST)
        etPembayaran!!.setAdapter(adapterType)
    }

    private fun getTiketById(id: Long) {
        // fungsi untuk menampilkan data Tiket berdasarkan id
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET,
                TiketApi.GET_BY_ID_URL + id,
                Response.Listener { response ->
                    val gson = Gson()
                    val Tiket = gson.fromJson(response, Tiket::class.java)

                    etNama!!.setText(Tiket.nama)
                    etJumlah!!.setText(Tiket.jumlah)
                    etWisata!!.setText(Tiket.wisata)
                    etPembayaran!!.setText(Tiket.pembayaran)
                    setExposedDropDownMenu()

                    Toast.makeText(
                        this@activity_beli_tiket,
                        "Data berhasil diambil!",
                        Toast.LENGTH_SHORT
                    ).show()
                    setLoading(false)
                },
                Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@activity_beli_tiket,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@activity_beli_tiket, e.message, Toast.LENGTH_SHORT).show()
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
    }

    private fun createTiket() {
        setLoading(true)

        if(etNama!!.text.toString().isEmpty()){
            Toast.makeText(this@activity_beli_tiket, "Nama Penumpang Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etJumlah!!.text.toString().isEmpty()){
            Toast.makeText(this@activity_beli_tiket, "Jumlah Penumpang Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etWisata!!.text.toString().isEmpty()){
            Toast.makeText(this@activity_beli_tiket, "Wisata Tujuan  Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etPembayaran!!.text.toString().isEmpty()){
            Toast.makeText(this@activity_beli_tiket, "Pilihan Pembayaran Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else{
            val Tiket = Tiket(
                etNama!!.text.toString(),
                etJumlah!!.text.toString(),
                etWisata!!.text.toString(),
                etPembayaran!!.text.toString(),

                )

            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, TiketApi.ADD_URL, Response.Listener { response ->
                    val gson = Gson()
                    var Tiket = gson.fromJson(response, Tiket::class.java)

                    if (Tiket != null)
                        Toast.makeText(this@activity_beli_tiket, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()

                    setLoading(false)
                }, Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@activity_beli_tiket,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@activity_beli_tiket, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray {
                        val gson = Gson()
                        val requestBody = gson.toJson(Tiket)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
            queue!!.add(stringRequest)
        }
        setLoading(false)

    }

    private fun updateTiket(id: Long){
        setLoading(true)
        val Tiket = Tiket(
            etNama!!.text.toString(),
            etJumlah!!.text.toString(),
            etWisata!!.text.toString(),
            etPembayaran!!.text.toString()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, TiketApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var Tiket = gson.fromJson(response, Tiket::class.java)

                if(Tiket != null)
                    Toast.makeText(this@activity_beli_tiket, "Data berhasil diubah", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@activity_beli_tiket, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(Tiket)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}