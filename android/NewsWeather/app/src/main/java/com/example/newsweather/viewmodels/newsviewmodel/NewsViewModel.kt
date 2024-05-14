package com.example.newsweather.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsweather.api.news.NewsApiInterface
import com.example.newsweather.api.news.NewsApi
import com.example.newsweather.api.weather.Weather
import com.example.newsweather.api.weather.WeatherApiInterface
import com.example.newsweather.database.roomdb.News
import com.example.newsweather.database.roomdb.NewsDatabaseDao
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

class NewsViewModel(val database: NewsDatabaseDao,
                    application: Application) : AndroidViewModel(application) {
    private val _allDataListFromRoom = MutableLiveData<List<News?>>().apply { value = emptyList() }
    val allDataListFromRoom: LiveData<List<News?>>
        get() = _allDataListFromRoom
    private val _temperature = MutableLiveData<Double?>()
    val temperature: LiveData<Double?>
        get() = _temperature
    val _categoryDataListFromRoom = MutableLiveData<List<News?>>()
    var category: String = "all"
    var currentPage = 0
    private val pageSize = 4 // Number of items per page
    var searchFlag: Boolean = false;



    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun newsGetMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://inshortsapi.vercel.app/")
            .build()
            .create(NewsApiInterface::class.java)

        val apiCatagoryList: ArrayList<String> = arrayListOf("news?category=national", "news?category=business",
            "news?category=sports", "news?category=world",
            "news?category=politics", "news?category=technology", "news?category=startup", "news?category=entertainment",
            "news?category=miscellaneous", "news?category=hatke", "news?category=science", "news?category=automobile")
//        val apiCatagoryList: ArrayList<String> = arrayListOf("news?category=technology"
//        )


        for (newsCatogory in apiCatagoryList){
            val retrofitData = retrofitBuilder.getData(newsCatogory)

            retrofitData.enqueue(object : Callback<NewsApi?> {
                override fun onResponse(
                    p0: Call<NewsApi?>,
                    p1: Response<NewsApi?>
                ) {
//                    Log.e("main activity", newsCatogory)
                    val responseBody = p1?.body()!!
                    for (data in responseBody.data){
                        dbInsert(responseBody.category, data.author, data.content,
                            data.date, data.id, data.imageUrl, data.readMoreUrl,
                            data.time, data.title, data.url, responseBody.success)
                    }
                }

                override fun onFailure(p0: Call<NewsApi?>, p1: Throwable) {
                    Log.d("MainActivity","on Failure"+ p1.message)
                }
            })
        }
    }

    fun weatherGetMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.tomorrow.io/v4/weather/")
            .build()
            .create(WeatherApiInterface::class.java)


        val retrofitData = retrofitBuilder.getData("realtime?location=madurai&apikey=83ASz9eG4Mblxzt2MkhmocPAvHe3wzD3")


        retrofitData.enqueue(object : Callback<Weather?> {
            override fun onResponse(
                p0: Call<Weather?>,
                p1: Response<Weather?>
            ) {
                val responseBody = p1?.body()!!
                _temperature.value = responseBody.data.values.temperature!!
            }
            override fun onFailure(p0: Call<Weather?>, p1: Throwable) {
                Log.d("MainActivity","on Failure"+ p1.message)
            }
        })
    }


    fun dbInsert(category: String, author: String, content: String,
                 date: String,id: String, imageUrl: String,
                 readMoreUrl: String,time: String,title: String,
                 url: String,success: String){
        uiScope.launch {
            val news = News(category = category, author = author, content = content,
                date = date, id = id, imageUrl = imageUrl, readMoreUrl = readMoreUrl,
                time = time, title = title, url = url, success = success)
            insert(news)
        }
    }

    private suspend fun insert(news: News){
        return withContext(Dispatchers.IO){
            database.insert(news)
        }
    }

    fun dbRetrieve(){
        uiScope.launch {
            get()
        }
    }

    private suspend fun get(){
        return withContext(Dispatchers.IO){
//            delay(1000)


            _allDataListFromRoom.postValue(database.getAll(pageSize, currentPage * pageSize))
//            _allDataListFromRoom.postValue(database.getAll())
            currentPage++
            Log.i("newviewmodel", "get all is running")
        }
    }

    fun dbRetrieveCategory(category: String, whereToAdd: String){
        uiScope.launch {
            getByCategory(category, whereToAdd)
        }
    }

    private suspend fun getByCategory(category: String, whereToAdd: String){
        return withContext(Dispatchers.IO){
            if(whereToAdd == "allData"){
                _allDataListFromRoom.postValue(database.getAllByCategory(category,pageSize, currentPage * pageSize ))
            }else{
                if(category == "all"){
                    _categoryDataListFromRoom.postValue(database.getAll())
                }else{
                    _categoryDataListFromRoom.postValue(database.getAllByCategory(category,pageSize, currentPage * pageSize ))
                }
            }
            currentPage++
            Log.i("newviewmodel", "getbycategory is running")
        }
    }

    fun dbRetrieveBySearch(query: String?, flag: String){
        uiScope.launch {
            search(query, flag)
        }
    }


    private suspend fun search(query: String?, flag: String){
        return withContext(Dispatchers.IO){
            if(flag == "scrollview"){
                _allDataListFromRoom.postValue(database.search(query, pageSize, currentPage * pageSize))
            }else{
                _categoryDataListFromRoom.postValue(database.search(query, pageSize, currentPage * pageSize))
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        database.clear()
    }

}


