package com.example.roomrecyclerretro.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountriesDatabaseDao {
    @Insert
    fun insert(countries: Countries)



    @Query("SELECT * FROM Countries")
    fun get(): List<Countries?>

    @Query("DELETE FROM Countries")
    fun clear()

//    @Query("SELECT * FROM employees ORDER BY employeeId DESC")
//    fun getAllEmployees(): LiveData<List<Employee>>

//    @Update
//    fun update(employee: Employee)
}