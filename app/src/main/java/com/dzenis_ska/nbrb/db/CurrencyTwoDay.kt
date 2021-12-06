package com.dzenis_ska.nbrb.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "currency_two_day")
data class CurrencyTwoDay(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "numCode")
    val numCode: String,
    @ColumnInfo(name = "charCode")
    val charCode: String,
    @ColumnInfo(name = "scale")
    val scale: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rate")
    val rate: String ,
    @ColumnInfo(name = "rate_yesterday")
    val rate_yesterday: String ,
    @ColumnInfo(name = "isCheck")
    var isCheck: Int = 0,
    @ColumnInfo(name = "numb")
    var numb: Int?
) : Parcelable