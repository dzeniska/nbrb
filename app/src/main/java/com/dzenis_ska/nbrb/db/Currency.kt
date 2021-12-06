package com.dzenis_ska.nbrb.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currency")
data class Currency(
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
    @ColumnInfo(name = "isCheck")
    var isCheck: Int = 0,
    @ColumnInfo(name = "numb")
    var numb: Int?
)