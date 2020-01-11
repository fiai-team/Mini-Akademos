package uci.fiai.miniakd.utils

import java.util.*

class DateUtils {

    companion object {

        /**
         * Get a date formatted as String
         * @param isShortMonthName A [Boolean] value, `true` for a small month name
         * @return A String formatted
         *
         */
        fun getFormattedDate(day: Int, month: Int, year: Int, isShortMonthName: Boolean): String {
            val locale = Locale.getDefault()
            val calendar = Calendar.getInstance(locale)
            calendar.set(year, month, day)


            return "$day de ${getMonthDisplayName(calendar, month, isShortMonthName)} de $year"
        }

        fun getMonthDisplayName(calendar: Calendar, month: Int, isShortMonthName: Boolean) {
            val locale = Locale.getDefault()
            val monthDisplayName = if (isShortMonthName) {
                val temp = "${calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)}."
                temp[0].toUpperCase()
                temp
            } else {
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
            }
        }
    }
}