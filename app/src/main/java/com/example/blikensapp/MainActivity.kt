package com.example.blikensapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.blikensapp.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val accessToken = "r392jnfv2-almf92nww1-ld:fwfa"
        //val url = "http://localhost:8080/api/blik/"
        val url = "http://192.168.1.45:8080/api/blik/"
        binding.button.setOnClickListener {

            getDataFromApi(url,accessToken)

        }
    }

    private fun getDataFromApi(url: String, accessToken: String) {
        val queue = Volley.newRequestQueue(this)
        val jsonBody = JSONObject()
        jsonBody.put("accountId","2")
        jsonBody.put("creationDate","2022-12-07 23:00:00")
        jsonBody.put("expirationDate","2022-12-07 23:11:11")

        val requestBody = jsonBody.toString()

        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                Log.d("Response", response.toString())
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Log.d("Error.Response", error.toString())
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["access-token"] = accessToken
                return headers
            }
        }

        queue.add(stringRequest)
    }
}