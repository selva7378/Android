package com.example.roomrecyclerretro.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.roomrecyclerretro.ApiInterface
import com.example.roomrecyclerretro.database.Countries
import com.example.roomrecyclerretro.database.CountriesDatabaseDao
import com.example.roomrecyclerretro.networklayer.CountriesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryViewModel(
    val database: CountriesDatabaseDao,
    application: Application
) : AndroidViewModel(application){

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var dataListFromApi: List<CountriesItem?> = listOf()
    var dataListFromRoom: List<Countries?> = listOf()

    init{

    }


    fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://restcountries.com/v3.1/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<CountriesItem?>?> {
            override fun onResponse(
                p0: Call<List<CountriesItem?>?>,
                p1: Response<List<CountriesItem?>?>
            ) {
                dataListFromApi = p1?.body()!!
                for (country in dataListFromApi){
                    dbInsert(country?.name?.common!!,country?.flags?.png!!)
                }
                dbRetrieve()
            }

            override fun onFailure(p0: Call<List<CountriesItem?>?>, p1: Throwable) {
                Log.d("MainActivity","on Failure"+ p1.message)
            }
        })
    }

    fun dbInsert(name: String, flag: String){
        uiScope.launch {
            val country = Countries(name = name, flag =  flag)
            insert(country)
        }
    }

    private suspend fun insert(country: Countries){
        return withContext(Dispatchers.IO){
            database.insert(country)
        }
    }

    fun dbRetrieve(){
        uiScope.launch {
            get()
        }
    }

    private suspend fun get(){
        return withContext(Dispatchers.IO){
            dataListFromRoom = database.get()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}