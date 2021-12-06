package com.dzenis_ska.nbrb.db

object ParseCourse {

    var listCur = arrayListOf<Currency>()

    fun getListCurrency(text: String): ArrayList<Currency>{
        listCur.clear()
        sub(text, "Id=\"", "\">", "id")
        sub(text, "<NumCode>", "</NumCode>", "NumCode")
        sub(text, "<CharCode>", "</CharCode>", "CharCode")
        sub(text, "<Scale>", "</Scale>", "Scale")
        sub(text, "<Name>", "</Name>", "Name")
        sub(text, "<Rate>", "</Rate>", "Rate")
        return listCur
    }

    private fun sub(apiC: String, delimeter1: String, delimeter2: String, par: String) {

        val sub1 = apiC.substringAfter(delimeter1)

        val list = sub1.split(delimeter1)
        val currency = Currency(0, "","","","","",0, null)
        when (par){
            "id"-> {
                list.forEach {
                    val id = it.substringBefore(delimeter2 )
                    listCur.add(currency.copy(id = id.toInt()))
                }
            }
            "NumCode"-> {
                for(i in list.indices){
                    listCur[i] = listCur[i].copy(numCode = list[i].substringBefore(delimeter2))
                }
            }
            "CharCode" -> {
                for(i in list.indices){
                    listCur[i] = listCur[i].copy(charCode = list[i].substringBefore(delimeter2))
                }
            }
            "Scale" -> {
                for(i in list.indices){
                    listCur[i] = listCur[i].copy(scale = list[i].substringBefore(delimeter2))
                }
            }
            "Name" -> {
                for(i in list.indices){
                    listCur[i] = listCur[i].copy(name = list[i].substringBefore(delimeter2))
                }
            }
            "Rate" -> {
                for(i in list.indices){
                    listCur[i] = listCur[i].copy(rate = list[i].substringBefore(delimeter2))
                }
            }
        }
    }
}