package com.example.roomdb.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDatabaseDao {
    @Insert
    fun insert(employee: Employee)



    @Query("SELECT * FROM employees ORDER BY employeeId DESC LIMIT 1")
    fun get(): LiveData<Employee?>

    @Query("DELETE FROM employees")
    fun clear()

//    @Query("SELECT * FROM employees ORDER BY employeeId DESC")
//    fun getAllEmployees(): LiveData<List<Employee>>

//    @Update
//    fun update(employee: Employee)
}