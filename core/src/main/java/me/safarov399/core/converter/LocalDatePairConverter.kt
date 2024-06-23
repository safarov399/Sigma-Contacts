package me.safarov399.core.converter

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDatePairConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    fun fromSignificantDateAndLabel(value: MutableList<Pair<LocalDate, String>>): String {
        val list = value.map { it.first.format(formatter) to it.second }
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toSignificantDateAndLabel(value: String): MutableList<Pair<LocalDate, String>> {
        val list = Json.decodeFromString<List<Pair<String, String>>>(value)
        return list.map { LocalDate.parse(it.first, formatter) to it.second }.toMutableList()
    }
}