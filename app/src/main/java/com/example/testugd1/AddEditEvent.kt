package com.example.testugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testugd1.api.EventApi
import com.example.testugd1.models.Event
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditEvent : AppCompatActivity() {

    companion object {
        private val TIPE_LIST = arrayOf("Konser Musik", "Pagelaran Budaya", "Pameran Seni", "Tarian Daerah")
    }

    private var etNama: EditText? = null
    private var etLokasi: EditText? = null
    private var etHarga: EditText? = null
    private var etKategori: AutoCompleteTextView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_event)

        queue = Volley.newRequestQueue(this)
        etNama = findViewById(R.id.et_nama)
        etLokasi = findViewById(R.id.et_Lokasi)
        etHarga = findViewById(R.id.et_Harga)
        etKategori = findViewById(R.id.et_Kategori)
        layoutLoading = findViewById(R.id.layout_loading)

        setExposedDropDownMenu()

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.addEvent)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Event")
            btnSave.setOnClickListener { createEvent() }
        } else {
            tvTitle.setText("Edit Event")
            getEventById(id)
            btnSave.setOnClickListener { updateEvent(id) }
        }
    }

    fun setExposedDropDownMenu() {
        val adapterKategori: ArrayAdapter<String> = ArrayAdapter<String>(this,
            R.layout.event_list, TIPE_LIST)
        etKategori!!.setAdapter(adapterKategori)

    }

    private fun getEventById(id: Long) {
        // fungsi untuk menampilkan data Event berdasarkan id
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET,
                EventApi.GET_BY_ID_URL + id,
                Response.Listener { response ->
                    val gson = Gson()
                    val event = gson.fromJson(response, Event::class.java)

                    etNama!!.setText(event.nama)
                    etLokasi!!.setText(event.lokasi)
                    etHarga!!.setText(event.harga)
                    etKategori!!.setText(event.kategori)
                    setExposedDropDownMenu()

                    Toast.makeText(
                        this@AddEditEvent,
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
                            this@AddEditEvent,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@AddEditEvent, e.message, Toast.LENGTH_SHORT).show()
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

    private fun createEvent() {
        setLoading(true)

        if(etNama!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditEvent, "Nama Event Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etLokasi!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditEvent, "Lokasi Event Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etHarga!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditEvent, "Harga Event Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etKategori!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditEvent, "Tipe Event Tidak boleh Kosong!", Toast.LENGTH_SHORT).show()
        }
        else{
            val event = Event(
                etNama!!.text.toString(),
                etLokasi!!.text.toString(),
                etHarga!!.text.toString(),
                etKategori!!.text.toString(),

                )

            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, EventApi.ADD_URL, Response.Listener { response ->
                    val gson = Gson()
                    var event = gson.fromJson(response, Event::class.java)

                    if (event != null)
                        Toast.makeText(this@AddEditEvent, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

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
                            this@AddEditEvent,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@AddEditEvent, e.message, Toast.LENGTH_SHORT).show()
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
                        val requestBody = gson.toJson(event)
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

    private fun updateEvent(id: Long){
        setLoading(true)
        val event = Event(
            etNama!!.text.toString(),
            etLokasi!!.text.toString(),
            etHarga!!.text.toString(),
            etKategori!!.text.toString()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, EventApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var Event = gson.fromJson(response, Event::class.java)

                if(event != null)
                    Toast.makeText(this@AddEditEvent, "Data berhasil diubah", Toast.LENGTH_SHORT).show()

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
                    Toast.makeText(this@AddEditEvent, e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(event)
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