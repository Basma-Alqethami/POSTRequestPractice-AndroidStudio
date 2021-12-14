package com.example.postrequestpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var rvAdapter: RVAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var addButton: Button
    lateinit var etName : EditText
    lateinit var etLocation : EditText

    var list = ArrayList<dataItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(list)
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        addButton = findViewById(R.id.addButton)
        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)

        getAllData()
        addButton.setOnClickListener { postData() }
    }

    fun getAllData() {
        val api = Client().getClient()?.create(API::class.java)

        api?.getData()?.enqueue(object : Callback<data> {
            override fun onResponse(
                call: Call<data>,
                response: Response<data>
            ) {
                for (item in response.body()!!) {
                    Log.d("item", "$item")
                    val name = item.name
                    val location = item.location
                    val pk = item.pk
                    list.add(dataItem(location, name, pk))
                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<data>, t: Throwable) {
                Log.d("error", "$t")
            }
        })
    }

    fun postData() {
        val name = etName.text.toString()
        val location = etLocation.text.toString()
        val pk = 0

        if(name.isNotEmpty() && location.isNotEmpty() ) {
            val api = Client().getClient()?.create(API::class.java)

            api?.postData(dataItem(location, name, pk))?.enqueue(object : Callback<dataItem> {
                override fun onResponse(call: Call<dataItem>, response: Response<dataItem>) {
                    getAllData()
                    etLocation.clearFocus()
                    etName.clearFocus()
                    etLocation.text.clear()
                    etName.text.clear()
                    Toast.makeText(applicationContext, "User added!", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<dataItem>, t: Throwable) {
                    Log.d("error", "$t")
                }
            })
        }else{
            Toast.makeText(applicationContext, "please enter your name and location", Toast.LENGTH_LONG).show()
        }
    }
}