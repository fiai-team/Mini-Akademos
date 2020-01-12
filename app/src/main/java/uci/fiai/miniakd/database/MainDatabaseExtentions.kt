package uci.fiai.miniakd.database

import android.content.Context
import uci.fiai.miniakd.database.entities.Student

fun addStudent(
    context: Context,
    student: Student
) {
    MainDatabase.instance(context).students.insertAll(student)
}