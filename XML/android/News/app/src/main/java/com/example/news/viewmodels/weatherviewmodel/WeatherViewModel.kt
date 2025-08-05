package com.example.news.viewmodels.weatherviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.api.weatherapi.Weather
import com.example.news.api.weatherapi.WeatherApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel: ViewModel() {
    private val _temperature = MutableLiveData<String?>()
    val temperature: LiveData<String?>
        get() = _temperature

    var doubleTemperature: Double = 0.0

    var location : String = ""

    fun getMyWeatherData(location: String){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.tomorrow.io/v4/")
            .build()
            .create(WeatherApiInterface::class.java)



            val retrofitData = retrofitBuilder.getWeather(location, "83ASz9eG4Mblxzt2MkhmocPAvHe3wzD3")

            retrofitData.enqueue(object : Callback<Weather?> {
                override fun onResponse(
                    p0: Call<Weather?>,
                    p1: Response<Weather?>
                ) {
                    if (p1.isSuccessful) {
                        val responseBody = p1.body()
                        if (responseBody != null && responseBody.data != null) {
                            _temperature.value = responseBody.data.values.temperature.toString() + " °C"
                            doubleTemperature = responseBody.data.values.temperature
                        } else {
                            // Handle the case where response body or its data is null
                            _temperature.value = "N/A"
                            Log.e("WeatherData", "Response body or data is null")
                        }
                    } else {
                        // Handle the case where response is not successful (e.g., API call limit reached)
                        _temperature.value = "N/A"
                        Log.e("WeatherData", "Unsuccessful response: ${p1.code()}")
                    }
////                    Log.e("main activity", newsCatogory)
//                    val responseBody = p1?.body()!!
//                    _temperature.value = responseBody.data.values.temperature.toString() + " °C"

                }

                override fun onFailure(p0: Call<Weather?>, p1: Throwable) {
                    Log.d("MainActivity","on Failure"+ p1.message)
                }
            })

    }
}