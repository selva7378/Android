package com.example.roomrecyclerretro

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomrecyclerretro.database.CountriesDatabase
import com.example.roomrecyclerretro.viewmodel.CountryViewModel
import com.example.roomrecyclerretro.viewmodel.CountryViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        // Find the Button by ID
        val myButton: Button = findViewById(R.id.button)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val application = requireNotNull(this).application
        val dataSource = CountriesDatabase.getInstance(application).countriesDatabaseDao

        val countryViewModelFactory = CountryViewModelFactory(dataSource, application)

        val countryViewModel = ViewModelProvider(this, countryViewModelFactory)
            .get(CountryViewModel::class.java)

        // Set onClick listener for the Button
        myButton.setOnClickListener {
            Log.i("main activity", "get is pressed")
            countryViewModel.getMyData()
            recyclerView.adapter = AdapterClass(countryViewModel.dataListFromRoom)
        }
    }


}