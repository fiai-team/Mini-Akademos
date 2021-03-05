package uci.fiai.miniakd.database.enums

import java.lang.IndexOutOfBoundsException

enum class Assistance {
    ABSENT,
    PRESENT,
    JUSTIFIED,
    NONE;

    companion object {
        fun of(value: Int): Assistance {
            if (value < 0 || value > 3) {
                throw IndexOutOfBoundsException("")
            }
            return values()[value]
        }
    }

}