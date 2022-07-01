package com.sugarmaniac.timeSeries

import java.sql.Date

class StringData(date : Date, value : String){
    val date:Date = date
    val value:String = value
}

class FloatData(date : Date, value: Float){
    val date:Date = date
    val value:Float = value
}

class BooleanData(date : Date, value: Boolean){
    val date:Date = date
    val value:Boolean = value
}
