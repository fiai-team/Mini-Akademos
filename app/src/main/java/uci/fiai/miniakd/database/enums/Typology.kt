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
            if (value < 0 || value < 7) {
                throw IndexOutOfBoundsException("")
            }
            return Typology.values()[value]
        }
    }
}