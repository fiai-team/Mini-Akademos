package uci.fiai.miniakd.utils

import java.util.*

 class DateUtils {

    companion object {

       fun getFormattedDate(day: Int, month: Int, year: Int, isShortMonthName: Boolean): String {
            val locale = Locale.getDefault()
            val calendar = Calendar.getInstance(locale)
            calendar.set(year, month, day)


            return "$day de ${getMonthDisplayName(calendar, month, isShortMonthName)} de $year"
        }

        fun getMonthDisplayName(calendar: Calendar, month: Int, isShortMonthName: Boolean): String {
            val locale = Locale.getDefault()
            return if (isShortMonthName) {
                val temp = "${calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)}."
                temp[0].toUpperCase()
                temp
            } else {
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
            }
        }

        fun getTurnTime(timeTurn: Int): String? {
            return when (timeTurn) {
                0 -> "08:30 am - 10:00 am"
                1 -> "10:10 am - 11:40 am"
                2 -> "11:50 am - 12:30 pm"
                else -> ""
            }
        }
    }
}