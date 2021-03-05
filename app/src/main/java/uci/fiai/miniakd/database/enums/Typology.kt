package uci.fiai.miniakd.database.enums

import java.lang.IndexOutOfBoundsException

enum class Typology {
    CONFERENCE,
    PRACTICE_CLASS,
    LABORATORY,
    TALLER,
    SEMINARY,
    PARTIAL_EXAM,
    FINAL_EXAM,
    EXTRAORDINARY_ACTIVITY;

    companion object {
        fun of(value: Int): Typology {
            if (value < 0 || value > 7) {
                throw IndexOutOfBoundsException("")
            }
            return Typology.values()[value]
        }
        fun initialLetter(typology: Typology) : String {
            return when (typology) {
                CONFERENCE -> "C"
                PRACTICE_CLASS -> "PC"
                LABORATORY -> "L"
                TALLER -> "T"
                SEMINARY -> "S"
                PARTIAL_EXAM -> "PP"
                FINAL_EXAM -> "PF"
                EXTRAORDINARY_ACTIVITY -> "EX"
            }
        }
    }

    val initialLetter: String?
        get() {
            return Typology.initialLetter(this)
        }
}

