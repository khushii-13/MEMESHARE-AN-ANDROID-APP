package com.example.memeshaare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONException


class MainActivity : AppCompatActivity() {
   var currentimageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()

    }
    private fun loadmeme(){
        val progressBar=findViewById<ProgressBar>(R.id.progress)
        progressBar.visibility=View.VISIBLE

        val url = "https://meme-api.com/gimme"
        val im=findViewById<ImageView>(R.id.nameImage)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
               currentimageurl= response.getString("url")
                Glide.with(this).load(currentimageurl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(im)
            },
            Response.ErrorListener {
                Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(request)
    }





    fun sharememe(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey!!check this cool meme $currentimageurl")
        val chooser=Intent.createChooser(intent,"Share your mme using...")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {

  loadmeme()
    }
}