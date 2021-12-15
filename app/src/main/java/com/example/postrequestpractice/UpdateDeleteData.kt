package com.example.postrequestpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteData : AppCompatActivity() {

    lateinit var etID: EditText
    lateinit var etName: EditText
    lateinit var etLocation: EditText

    lateinit var btCancel: Button
    lateinit var btDelete: Button
    lateinit var btUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_data)


        etID = findViewById(R.id.etID)
        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)

        btCancel = findViewById(R.id.btCancel)
        btDelete = findViewById(R.id.btDelete)
        btUpdate = findViewById(R.id.btUpdate)

        val disData = intent.getSerializableExtra("displayData") as dataItem


        Log.d("gg", "$disData")
        etID.setText(disData.pk.toString())
        etName.setText(disData.name)
        etLocation.setText(disData.location)


        btCancel.setOnClickListener{
            val intent = Intent(this@UpdateDeleteData, MainActivity::class.java)
            startActivity(intent)
        }

        btDelete.setOnClickListener { Delete(disData) }
        btUpdate.setOnClickListener { Update(disData) }
    }

    fun Update(disData: dataItem) {

        val api = Client().getClient()?.create(API::class.java)

        api?.updateData(
            disData.pk, dataItem(
                etLocation.text.toString(),
                etName.text.toString(),
                disData.pk
            ))?.enqueue(object: Callback<dataItem> {
            override fun onResponse(call: Call<dataItem>, response: Response<dataItem>) {
                Toast.makeText(this@UpdateDeleteData, "User Updated", Toast.LENGTH_LONG).show()
                val intent = Intent(this@UpdateDeleteData, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<dataItem>, t: Throwable) {
                Toast.makeText(this@UpdateDeleteData, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })

    }

    fun Delete(disData: dataItem) {

        val api = Client().getClient()?.create(API::class.java)

        api?.deleteData(disData.pk)?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@UpdateDeleteData, "User Deleted", Toast.LENGTH_LONG).show()
                val intent = Intent(this@UpdateDeleteData, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UpdateDeleteData, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        })
    }
}