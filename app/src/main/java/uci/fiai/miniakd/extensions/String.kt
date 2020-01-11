package uci.fiai.miniakd.extensions

/**
 * Returns a upper-case string having leading and trailing whitespace removed.
 *
 * @return upper-case string having leading and trailing whitespace removed
 */
fun String.trimUpperCase(): String = this.trim().toUpperCase()