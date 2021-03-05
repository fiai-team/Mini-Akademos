package uci.fiai.miniakd.database.enums

import java.lang.IndexOutOfBoundsException
import java.time.DateTimeException
import java.time.Month

enum class Places {
    DOCENT1,
    Docent2,
    Docent3,
    Docent4,
    Docent5,
    Docent6,
    SportsArea;

    companion object {
        fun of(value: Int): Places {
            if (value < 0 || value > 6) {
                throw IndexOutOfBoundsException("")
            }
            return values()[value]
        }
    }
}





