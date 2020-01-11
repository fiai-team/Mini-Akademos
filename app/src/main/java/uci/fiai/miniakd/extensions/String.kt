package uci.fiai.miniakd.extensions

import java.util.*

/**
 * Returns a upper-case string having leading and trailing whitespace removed.
 *
 * @return upper-case string having leading and trailing whitespace removed
 */
fun String.trimUpperCase(): String = this.trim().toUpperCase(Locale.getDefault())

val String.Companion.empty: String
    get() { return "" }
