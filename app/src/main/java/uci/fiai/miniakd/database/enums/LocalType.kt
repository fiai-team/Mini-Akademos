package uci.fiai.miniakd.database.enums

import java.lang.IndexOutOfBoundsException

enum class LocalType {
    Salon,
    AULA,
    Laboratory;

    companion object {
        fun of(value: Int): LocalType {
            if (value < 0 || value > 2) {
                throw IndexOutOfBoundsException("")
            }
            return LocalType.values()[value]
        }
    }
}