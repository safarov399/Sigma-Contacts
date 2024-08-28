package me.safarov399.core.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhoneNumbersConverter {
    @TypeConverter
    fun fromList(numbers: MutableList<String>?): String? {
        return Gson().toJson(numbers)
    }

    @TypeConverter
    fun toList(numbersString: String?): MutableList<String>? {
        if (numbersString == null) {
            return mutableListOf()
        } else {
            val listType = object : TypeToken<MutableList<String>>() {}.type
            return Gson().fromJson(numbersString, listType)
        }
    }
}