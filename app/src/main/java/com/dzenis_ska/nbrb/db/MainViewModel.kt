package com.dzenis_ska.nbrb.db

import android.util.Log
import androidx.lifecycle.*
import com.dzenis_ska.nbrb.remote_model.ApiCourse
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(dataBase: MainDataBase) : ViewModel() {

    var isSave: Boolean = false
    val dao = dataBase.getDao()
    var isGetList: Int = 0

    private var listFirst = emptyList<CurrencyTwoDay>()
    private fun getListFirst() {
        viewModelScope.launch {
            listFirst = dao.getAll()
        }
    }


    val today = TimeManager.getCurrentDay()
    val yesterday = TimeManager.getYesterday()
    val tomorrow = TimeManager.getTomorrow()


    private val _dayList = MutableLiveData<ArrayList<String>>()
    val dayList: LiveData<ArrayList<String>> = _dayList

    private val _listCurr = MutableLiveData<ArrayList<CurrencyTwoDay>>()
    val listCurr: LiveData<ArrayList<CurrencyTwoDay>> = _listCurr

    val listCurrencyTwoDay : LiveData<List<CurrencyTwoDay>> = dao.getAllCourses().asLiveData()

    private val apiCourse = ApiCourse.create()

    fun getListForSettings(){
        viewModelScope.launch {
            _listCurr.value = dao.getAll() as ArrayList<CurrencyTwoDay>
        }
    }

    fun clearDays(){
        _dayList.value = arrayListOf("", "")
    }

    fun getC(callback: (bool: Boolean) -> Unit) {
        getListFirst()
        viewModelScope.launch {

            val text = getTextCurrency(tomorrow)

            var isCheck: Int
            var numb = 0
            if(text != EXCEPTION && text.length > 100){

                val listTomorrow = arrayListOf<Currency>()
                    getListCurr(tomorrow){ list, bool ->
                        if(bool) {
                            isGetList++
                            listTomorrow.addAll(list)
                        } else {
                            isGetList--
                        }
                    }

                val listToday = arrayListOf<Currency>()
                    getListCurr(today){ list, bool ->
                        if(bool) {
                            isGetList++
                            listToday.addAll(list)
                        } else {
                            isGetList--
                        }
                    }
                if(isGetList == 2) {
                    _dayList.value = arrayListOf(TimeManager.dayToday(), TimeManager.dayTomorrow())
                    val listCurr = arrayListOf<CurrencyTwoDay>()
                    for (i in listTomorrow.indices) {
                        val cC = listTomorrow[i].charCode
                        isCheck = if (cC == "USD" || cC == "EUR" || cC == "RUB") 1 else 0
                        listCurr.add(
                            CurrencyTwoDay(
                                listTomorrow[i].id,
                                listTomorrow[i].numCode,
                                listTomorrow[i].charCode,
                                listTomorrow[i].scale,
                                listTomorrow[i].name,
                                listTomorrow[i].rate,
                                listToday[i].rate,
                                isCheck,
                                numb++
                            )
                        )
                    }
                    isGetList = 0
                    if (listFirst.isEmpty()) {
                        dao.insertCourses(listCurr)
                    } else {
                        listCurr.forEach { updateRate ->
                            dao.insertOneCourse(
                                updateRate.rate,
                                updateRate.rate_yesterday,
                                updateRate.id
                            )
                        }
                    }
                } else {
                    isGetList = 0
                    callback(false)
                }
            } else {
                val listToday = arrayListOf<Currency>()
                getListCurr(today){ list, bool ->
                    if(bool) {
                        isGetList++
                        listToday.addAll(list)
                    } else {
                        isGetList--
                    }
                }
                val listYesterday = arrayListOf<Currency>()
                getListCurr(yesterday){  list, bool ->
                    if(bool) {
                        isGetList++
                        listYesterday.addAll(list)
                    } else {
                        isGetList--
                    }
                }
                if(isGetList == 2) {
                    _dayList.value = arrayListOf(TimeManager.dayYesterday(), TimeManager.dayToday())
                    val listCurr = arrayListOf<CurrencyTwoDay>()
                    for (i in listToday.indices) {
                        val cC = listToday[i].charCode
                        isCheck = if (cC == "USD" || cC == "EUR" || cC == "RUB") 1 else 0
                        listCurr.add(
                            CurrencyTwoDay(
                                listToday[i].id,
                                listToday[i].numCode,
                                listToday[i].charCode,
                                listToday[i].scale,
                                listToday[i].name,
                                listToday[i].rate,
                                listYesterday[i].rate,
                                isCheck,
                                numb++
                            )
                        )
                    }

                    isGetList = 0
                    if (listFirst.isEmpty()) {
                        dao.insertCourses(listCurr)
                    } else {
                        listCurr.forEach { updateRate ->
                            dao.insertOneCourse(
                                updateRate.rate,
                                updateRate.rate_yesterday,
                                updateRate.id
                            )
                        }
                    }
                } else {
                    isGetList = 0
                    callback(false)
                }
            }
        }
    }

    private suspend fun getListCurr(day: String, callback: (listCur: ArrayList<Currency>, bool: Boolean) -> Unit)  {
        val text = getTextCurrency(day)
        val list = arrayListOf<Currency>()
        if(text != EXCEPTION || text.length > 100){
            val listCur = ParseCourse.getListCurrency(text)
            list.addAll(listCur)
            callback(list, true)
        } else {
            callback(list, false)
        }
    }

    private suspend fun getTextCurrency(day: String): String{
        return try{
            val text = apiCourse.getCourse(day)
            Log.d("!!!ex", "${text}")
            text
        } catch (e : Exception){
            Log.d("!!!ex", e.toString())
            EXCEPTION
        }
    }

    fun replaseNumb(newListAfterMove: ArrayList<CurrencyTwoDay>) {
        viewModelScope.launch {

//            dao.deleteAll()
            dao.insertCourses(newListAfterMove as MutableList<CurrencyTwoDay>)

            newListAfterMove.forEach {
                Log.d("!!!move", "${it.numb}")
//                dao.replace(it.numb!!, it.id)
            }

        }
    }

    class MainViewModelFactory(val dataBase: MainDataBase) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(dataBase) as T
            }
            throw IllegalArgumentException("Uncnown ViewModelClass")
        }
    }

    companion object {
        const val EXCEPTION = "xyй"
    }
}