package com.example.news.viewmodels.newsviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.api.newsapi.ApiInterface
import com.example.news.api.newsapi.NewsApi
import com.example.news.roomdb.news.News
import com.example.news.roomdb.news.NewsDatabaseDao
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
                    private val application: Application) : AndroidViewModel(application) {
    private val _allDataListFromRoom = MutableLiveData<List<News?>>().apply { value = emptyList() }
    val allDataListFromRoom: LiveData<List<News?>>
        get() = _allDataListFromRoom

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    val _categoryDataListFromRoom = MutableLiveData<List<News?>>()
    var currentPage = 0
    private val pageSize = 4 // Number of items per page
    var searchFlag: Boolean = false;
    var category: String = "all"



    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://inshortsapi.vercel.app/")
            .build()
            .create(ApiInterface::class.java)

        val apiCategoryList: ArrayList<String> = arrayListOf("news?category=national", "news?category=business",
            "news?category=sports", "news?category=world",
            "news?category=politics", "news?category=technology", "news?category=startup", "news?category=entertainment",
            "news?category=miscellaneous", "news?category=hatke", "news?category=science", "news?category=automobile")
//        val apiCatagoryList: ArrayList<String> = arrayListOf("news?category=technology"
//        )


        for (newsCategory in apiCategoryList){
            val retrofitData = retrofitBuilder.getData(newsCategory)
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
                    if (newsCategory == apiCategoryList.last()) {
                        _dataLoading.value = false
                    }
                }

                override fun onFailure(p0: Call<NewsApi?>, p1: Throwable) {
                    Log.d("MainActivity","on Failure"+ p1.message)
                    if (newsCategory == apiCategoryList.last()) {
                        _dataLoading.value = false
                    }
                }
            })
        }
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

    fun dbRetrieveBySearch(query: String, flag: String){
        uiScope.launch {
            search(query, flag)
        }
    }


    private suspend fun search(query: String, flag: String){
        return withContext(Dispatchers.IO){
            if(flag == "scrollview"){
                _allDataListFromRoom.postValue(database.search(query, pageSize, currentPage * pageSize))
            }else{
                _categoryDataListFromRoom.postValue(database.search(query, pageSize, currentPage * pageSize))
            }
            currentPage++
        }
    }

    fun dbClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        return withContext(Dispatchers.IO){
            database.clear()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            application.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


}


