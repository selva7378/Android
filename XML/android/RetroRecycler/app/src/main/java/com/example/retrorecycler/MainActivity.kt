package com.example.retrorecycler

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrorecycler.datalayer.ApiInterface
import com.example.retrorecycler.datalayer.CountryFlag
import com.example.retrorecycler.datalayer.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: List<Data>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        // Find the Button by ID
        val myButton: Button = findViewById(R.id.button)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = listOf()
        // Set onClick listener for the Button
        myButton.setOnClickListener {
            getMyData()
        }
    }

    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://countriesnow.space/api/v0.1/countries/flag/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<CountryFlag?> {
            override fun onResponse(
                p0: Call<CountryFlag?>,
                p1: Response<CountryFlag?>
            ) {
                val responseBody = p1.body()!!

                dataList = responseBody?.data!!

                recyclerView.adapter = AdapterClass(dataList)

//                val myStringBuilder = StringBuilder()
//
//                for(countryData in responseBody?.data!!){
//                    myStringBuilder.append(countryData.name)
//                }

//                findViewById<TextView>(R.id.textid).setText(myStringBuilder)
            }


            override fun onFailure(p0: Call<CountryFlag?>, p1: Throwable) {
                Log.d("MainActivity","on Failure"+ p1.message)
            }
        })
    }
}