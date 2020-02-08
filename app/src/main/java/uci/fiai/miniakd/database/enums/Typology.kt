package uci.fiai.miniakd.database.enums

import java.lang.IndexOutOfBoundsException

enum class Typology {
    Conference,
    Practice_class,
    Laboratory,
    Taller,
    Seminary,
    Partial_exam,
    Final_exam,
    Extraordinary_activity;

    companion object {
        fun of(value: Int): Typology {
            if (value < 0 || value > 7) {
                throw IndexOutOfBoundsException("")
            }
            return Typology.values()[value]
        }
        fun initialLetter(typology: Typology) : String {
            return when (typology) {
                Conference -> "C"
                Practice_class -> "PC"
                Laboratory -> "L"
                Taller -> "T"
                Seminary -> "S"
                Partial_exam -> "PP"
                Final_exam -> "PF"
                Extraordinary_activity -> "EX"
            }
        }
    }
}

val Typology.initialLetter: String?
    get() {
        return Typology.initialLetter(this)
    }