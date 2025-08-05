package com.example.roomrecyclerretro.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Countries")
data class Countries(
    @PrimaryKey(autoGenerate = true)
    var countryId: Int = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "flag")
    var flag: String
)
