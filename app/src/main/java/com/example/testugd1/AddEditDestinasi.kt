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
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testugd1.api.DestinasiApi
import com.example.testugd1.models.Destinasi
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditDestinasi : AppCompatActivity() {
    companion object {
        private val TIPE_LIST = arrayOf("Alam", "Buatan")
    }

    private var etNama: EditText? = null
    private var etLokasi: EditText? = null
    private var etHarga: EditText? = null
    private var etType: AutoCompleteTextView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_destinasi)

        queue = Volley.newRequestQueue(this)
        etNama = findViewById(R.id.et_nama)
        etLokasi = findViewById(R.id.et_Lokasi)
        etHarga = findViewById(R.id.et_Harga)
        etType = findViewById(R.id.et_Tipe)
        layoutLoading = findViewById(R.id.layout_loading)

        setExposedDropDownMenu()

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.addDestinasi)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Destinasi")
            btnSave.setOnClickListener { createDestinasi() }
        } else {
            tvTitle.setText("Edit Destinasi")
            getDestinasiById(id)
            btnSave.setOnClickListener { updateDestinasi(id) }
        }
    }

    fun setExposedDropDownMenu() {
        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(this,
            R.layout.TIPE_LIST)
        etType!!.setAdapter(adapterType)

    }

    private fun getDestinasiById(id: Long) {
        // fungsi untuk menampilkan data Destinasi berdasarkan id
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Request.Method.GET,
                DestinasiApi.GET_BY_ID_URL + id,
                Response.Listener { response ->
                    val gson = Gson()
                    val destinasi = gson.fromJson(response, Destinasi::class.java)

                    etNama!!.setText(destinasi.nama)
                    etLokasi!!.setText(destinasi.lokasi)
                    etHarga!!.setText(destinasi.harga)
                    etType!!.setText(destinasi.tipe)
                    setExposedDropDownMenu()

                    Toast.makeText(
                        this@AddEditDestinasi,
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
                            this@AddEditDestinasi,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@AddEditDestinasi, e.message, Toast.LENGTH_SHORT).show()
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

    private fun createDestinasi() {
        setLoading(true)

        val destinasi = Destinasi(
            etNama!!.text.toString(),
            etLokasi!!.text.toString(),
            etHarga!!.text.toString(),
            etType!!.text.toString()
        )

        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, DestinasiApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                var destinasi = gson.fromJson(response, Destinasi::class.java)

                if (destinasi != null)
                    Toast.makeText(this@AddEditDestinasi, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

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
                        this@AddEditDestinasi,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditDestinasi, e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(destinasi)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)
    }

    private fun updateDestinasi(id: Long){
        setLoading(true)
        val destinasi = Destinasi(
            etNama!!.text.toString(),
            etLokasi!!.text.toString(),
            etHarga!!.text.toString(),
            etType!!.text.toString()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, DestinasiApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var Destinasi = gson.fromJson(response, Destinasi::class.java)

                if(destinasi != null)
                    Toast.makeText(this@AddEditDestinasi, "Data berhasil diubah", Toast.LENGTH_SHORT).show()

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
                    Toast.makeText(this@AddEditDestinasi, e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(destinasi)
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