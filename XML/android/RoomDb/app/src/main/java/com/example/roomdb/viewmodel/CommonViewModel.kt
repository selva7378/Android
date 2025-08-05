package com.example.roomdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.roomdb.database.Employee
import com.example.roomdb.database.EmployeeDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommonViewModel(
    val database: EmployeeDatabaseDao,
    application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _employee = MutableLiveData<LiveData<Employee?>>()
    val employee: LiveData<LiveData<Employee?>>
        get() = _employee


    init{
        initializeEmployee()
    }

    private fun initializeEmployee(){
        uiScope.launch {
            _employee.value = getEmployeeFromDatabase()
        }
    }

    private suspend fun getEmployeeFromDatabase(): LiveData<Employee?>{
        return withContext(Dispatchers.IO){
            var employee = database.get()
            employee
        }
    }

    fun onGo(name: String, email: String, phoneNo: String, occupation: String, address: String){
        uiScope.launch {
            val employee = Employee(
                name = name.toString(),
                email = email.toString(),
                phNumber = phoneNo.toString(),
                occupation = occupation.toString(),
                address = address.toString()
            )
            insert(employee)
        }
    }

    private suspend fun insert(employee: Employee){
        return withContext(Dispatchers.IO){
            database.insert(employee)
        }
    }

    fun onDelete(){
        uiScope.launch {
            delete()
        }
    }

    private suspend fun delete(){
        return withContext(Dispatchers.IO){
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}