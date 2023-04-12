package test.raul.kotlin.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateFormatter {

    companion object {


        fun getCorrectDayFormat(day: Int): String {

            var mDayString = "$day"
            if(day < 10){
                mDayString = "0$mDayString"
            }
            return mDayString
        }

        fun getCorrectMonthFormat(month: Int): String {
            var mMonth :  Int = month + 1
            var mMonthString = "$mMonth"
            if(month < 10){
                mMonthString = "0$mMonthString"
            }
            return mMonthString
        }

        fun toDate(date: String): LocalDate {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }

    }
}