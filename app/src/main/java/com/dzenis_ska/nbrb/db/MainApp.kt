package com.dzenis_ska.nbrb.db

import android.app.Application

class MainApp: Application() {
    val database by lazy { MainDataBase.getDataBase(this)}
}