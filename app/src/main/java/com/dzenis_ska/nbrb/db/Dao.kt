package com.dzenis_ska.nbrb.db


import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface  Dao {

    @Query("SELECT * FROM currency_two_day")
    fun getAllCourses(): Flow<List<CurrencyTwoDay>>

    @Query("SELECT * FROM currency_two_day")
    suspend fun getAll(): List<CurrencyTwoDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(newCourse: MutableList<CurrencyTwoDay>)

    @Query("UPDATE currency_two_day SET rate = :rate,  rate_yesterday = :rate_yesterday WHERE id = :id")
    suspend fun insertOneCourse(rate: String, rate_yesterday: String, id: Int)

    @Query("UPDATE currency_two_day SET numb = :startPos WHERE id = :targetPos")
    suspend fun replace(startPos: Int, targetPos: Int)


    @Query("DELETE  FROM currency_two_day")
    suspend fun deleteAll()

}