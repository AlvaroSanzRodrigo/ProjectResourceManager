package io.github.alvarosanzrodrigo.projectresourcemanager.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.Collections.emptyList
import com.google.gson.Gson
import java.util.*


class ListConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToStringList(data: String?): List<String> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<String>>() {

        }.type

        return gson.fromJson<List<String>>(data, listType)
    }

    @TypeConverter
    fun stringListToString(someObjects: List<String>): String {
        return gson.toJson(someObjects)
    }
}
