package uci.fiai.miniakd.extensions

import java.util.*

fun Calendar.getYear() : Int {
    return this.get(Calendar.YEAR)
}

fun Calendar.getMonth() : Int {
    return this.get(Calendar.MONTH)
}

fun Calendar.getDay() : Int {
    return this.get(Calendar.DAY_OF_MONTH)
}