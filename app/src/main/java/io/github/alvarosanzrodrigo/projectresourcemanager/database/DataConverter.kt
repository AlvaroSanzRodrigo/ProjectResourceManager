package io.github.alvarosanzrodrigo.projectresourcemanager.database



import android.arch.persistence.room.TypeConverter
import java.util.Date


class DataConverter {

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(value: Date?): Long? {
        return value?.time
    }
}