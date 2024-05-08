package com.example.retrofitapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        // Find the Button by ID
        val myButton: Button = findViewById(R.id.button)

        // Set onClick listener for the Button
        myButton.setOnClickListener {
            getMyData()
        }
    }

    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://countriesnow.space/api/v0.1/countries/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<CountryCapitalDataClass?> {
            override fun onResponse(
                p0: Call<CountryCapitalDataClass?>,
                p1: Response<CountryCapitalDataClass?>
            ) {
                val responseBody = p1.body()!!

                val myStringBuilder = StringBuilder()

                for(countryData in responseBody?.data!!){
                    myStringBuilder.append(countryData.name)
                }

                findViewById<TextView>(R.id.textid).setText(myStringBuilder)
            }


            override fun onFailure(p0: Call<CountryCapitalDataClass?>, p1: Throwable) {
                Log.d("MainActivity","on Failure"+ p1.message)
            }
        })
    }
}