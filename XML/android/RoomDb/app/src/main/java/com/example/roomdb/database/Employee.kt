package com.example.roomdb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Employees")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    var employeeId: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "phoneNumber")
    var phNumber: String,
    @ColumnInfo(name = "occupation")
    var occupation: String,
    @ColumnInfo(name = "address")
    var address: String
)
