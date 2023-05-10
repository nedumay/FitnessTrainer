package com.example.testproject.app.utils

import android.text.Editable
import android.text.TextWatcher
import java.util.*

/**
 * @author Nedumayy (Samim)
 * Mask to display the date and save the date in the format MM.DD.YYYY
 */
class DataMask : TextWatcher {

    private var updateText: String? = null
    private var editing = false

    companion object{
        private const val MAX_LENGTH = 8
        private const val MIN_LENGTH = 2
    }

    override fun beforeTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {}

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        if(text.toString() == updateText || editing) return

        var digits = text.toString().replace("\\D".toRegex(),"")
        val length = digits.length

        if(length <= MIN_LENGTH){
            digits = validateMonth(digits)
            updateText = digits
            return
        }
        if(length > MAX_LENGTH){
            digits = digits.substring(0, MAX_LENGTH)
        }

        updateText = if(length <= 4){
            digits = validateDay(digits.substring(0,2),digits.substring(2))
            val month = digits.substring(0,2)
            val day = digits.substring(2)
            String.format(Locale.US, "%s/%s", month, day)
        } else {
            digits = digits.substring(0,2) + digits.substring(2,4) + validateYear(digits.substring(4))
            val month = digits.substring(0,2)
            val day = digits.substring(2,4)
            val year = digits.substring(4)
            String.format(Locale.US, "%s/%s/%s", month,day,year)
        }
    }

    fun validateDay(month:String,day:String):String{
        val arr31 = intArrayOf(1,3,5,7,8,10,12)
        val arr30 = intArrayOf(4,6,9,11)
        val arrFeb = intArrayOf(2)

        if(day.length == 1 && ((day.toInt()>3 && month.toInt() !in arrFeb) || (day.toInt() > 2 && month.toInt() in arrFeb))){
            return month
        }

        return when(month.toInt()){
            in arr31 -> validateDay(month,arr31,day,31)
            in arr30 -> validateDay(month,arr30,day,30)
            in arrFeb -> validateDay(month,arrFeb,day,29)
            else -> "$month$day"
        }

    }

    fun validateDay(month:String, arr: IntArray,day:String,maxDay: Int):String{
        if(month.toInt() in arr){
            if(day.toInt() > maxDay){
                return "$month${day.substring(0,1)}"
            }
        }
        return "$month$day"
    }

    fun validateYear(year: String):String{
        if(year.length == 1 && (year.toInt() in 3..9 || year.toInt() == 0)) {
            return ""
        }
        if(year.length == 2 && year.toInt() !in 19..20){
            return year.substring(0,1)
        }
        return year
    }

    fun validateMonth(month: String):String{
        if(month.length == 1 && month.toInt() in 2..9){
            return "0$month"
        }

        if(month.length == 2 && month.toInt() > 12){
            return month.substring(0,1)
        }
        return month
    }

    override fun afterTextChanged(editable: Editable) {
        if(editing) return

        editing = true
        editable.clear()
        editable.insert(0,updateText)
        editing = false
    }
}