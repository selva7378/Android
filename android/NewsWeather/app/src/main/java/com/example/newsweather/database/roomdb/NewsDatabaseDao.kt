package com.example.newsweather.database.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDatabaseDao{
    @Insert
    fun insert(news: News)

    @Query("SELECT * FROM News")
    fun getAll(): List<News?>

    @Query("DELETE FROM News")
    fun clear()

    @Query("SELECT * FROM News WHERE category = :category")
    fun getAllByCategory(category: String): List<News?>

    @Query("SELECT * FROM News LIMIT :pageSize OFFSET :offset")
    fun getAll(pageSize: Int, offset: Int): List<News?>

}
