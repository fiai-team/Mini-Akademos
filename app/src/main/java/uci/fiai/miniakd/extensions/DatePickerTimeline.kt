package uci.fiai.miniakd.extensions

import com.github.badoualy.datepicker.DatePickerTimeline
import uci.fiai.miniakd.utils.DateUtils

fun DatePickerTimeline.selectedDateFormatted() : String {
    val day = this.selectedDay
    val month = this.selectedMonth
    val year = this.selectedYear

    return DateUtils.getFormattedDate(day, month, year, true)
}