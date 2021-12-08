package com.dzenis_ska.nbrb.db

import java.text.SimpleDateFormat
import java.util.*

object TimeManager {

    fun getTomorrow(): String{
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val resultDate = Date(System.currentTimeMillis() + 86400000)
    return sdf.format(resultDate)
    }
    fun getYesterday(): String{
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val resultDate = Date(System.currentTimeMillis() - 86400000)
        return sdf.format(resultDate)
    }
    fun getCurrentDay(): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val resultDate = Date(System.currentTimeMillis())
        return sdf.format(resultDate)
    }
    fun dayToday(): String{
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val resultDate = Date(System.currentTimeMillis())
        return sdf.format(resultDate)
    }
    fun dayTomorrow(): String{
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val resultDate = Date(System.currentTimeMillis() + 86400000)
        return sdf.format(resultDate)
    }
    fun dayYesterday(): String{
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val resultDate = Date(System.currentTimeMillis() - 86400000)
        return sdf.format(resultDate)
    }
}