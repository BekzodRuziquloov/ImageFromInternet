package space.beka.imagefrominternet

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import space.beka.imagefrominternet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var networkHelper: NetworkHelper
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

networkHelper= NetworkHelper(this)
        if (networkHelper.isNetworkConnected()){

            binding.tvName.text = "Connected"

            requestQueue = Volley.newRequestQueue(this)

            fetchImageLoad()

        }else{
            binding.tvName.text = "Disconnected"
        }

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity2::class.java))
        }


    }
    private fun fetchImageLoad() {
        val imageRequest = ImageRequest("https://i.imgur.com/Nwk25LA.jpg",
            object : Response.Listener<Bitmap>{
                override fun onResponse(response: Bitmap?) {
                    binding.image.setImageBitmap(response)
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tvName.text = error?.message
                }
            })

        requestQueue.add(imageRequest)
    }
    private fun featchObjectLoad() {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "http://ip.jsontest.com", null,
            object: Response.Listener<JSONObject>{//xatolik
            override fun onResponse(response: JSONObject?) {
                val strstring = response?.getString("ip")
                binding.tvName.text = strstring
            }
            }, object : Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    binding.tvName.text = error?.message
                }
            })
        requestQueue.add(jsonObjectRequest)
    }
}