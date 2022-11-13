package space.beka.imagefrominternet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import space.beka.imagefrominternet.adapters.RvAdapter
import space.beka.imagefrominternet.databinding.ActivityMain2Binding
import space.beka.imagefrominternet.models.Urls

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var requestQueue: RequestQueue
    lateinit var userAdapter: RvAdapter
    private lateinit var list: ArrayList<Urls>
    var url = "https://api.github.com/users"
    private val TAG = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)
        VolleyLog.DEBUG = true //qanday ma'lumot kelayotganini Logda ko'rsatib turadi

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {

                    val type = object : TypeToken<List<Urls>>() {}.type
                    val list = Gson().fromJson<List<Urls>>(response.toString(), type)

                    userAdapter = RvAdapter(list)
                    binding.rv.adapter = userAdapter

                    Log.d(TAG, "onResponse : ${response.toString()}")
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                }

            })

        doInBackground()

        jsonArrayRequest.tag = "tag1" //tag berilyapti
        requestQueue.add(jsonArrayRequest)

        requestQueue.cancelAll("tag1") // tag1 dagi so'rovlarni ortga qaytarish uchun
    }

    fun doInBackground(vararg params: Void?): Void? {

        val jsonArrayRequest = JsonArrayRequest(GET, url, null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {

                    list = ArrayList()
                    val type = object : TypeToken<List<Urls>>() {}.type
                    list = Gson().fromJson<List<Urls>>(response.toString(), type) as ArrayList<Urls>
                    userAdapter = RvAdapter(list)
                    binding.rv.adapter = userAdapter
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(binding.root.context, "$error", Toast.LENGTH_SHORT).show()
                }
            })

        requestQueue.add(jsonArrayRequest)
        return null
    }
}