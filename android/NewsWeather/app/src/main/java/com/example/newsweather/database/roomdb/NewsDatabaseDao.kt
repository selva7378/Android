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

    @Query("SELECT * FROM News WHERE category = :category LIMIT :pageSize OFFSET :offset")
    fun getAllByCategory(category: String, pageSize: Int, offset: Int): List<News?>

    @Query("SELECT * FROM News LIMIT :pageSize OFFSET :offset")
    fun getAll(pageSize: Int, offset: Int): List<News?>

    @Query("SELECT * FROM News WHERE title LIKE '%' || :query || '%' LIMIT :pageSize OFFSET :offset")
    fun search(query: String?, pageSize: Int, offset: Int): List<News>

}
