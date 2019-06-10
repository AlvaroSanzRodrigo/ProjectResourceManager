package io.github.alvarosanzrodrigo.projectresourcemanager.database

import android.arch.persistence.room.TypeConverter
import io.github.alvarosanzrodrigo.projectresourcemanager.enums.DocumentTypes


class DocumentTypeConverter{

    @TypeConverter
    fun toDatcumentType(value: String?): DocumentTypes? {
        return when (value){
            "PICTURE" -> DocumentTypes.PICTURE
            "VIDEO" -> DocumentTypes.VIDEO
            "AUDIO" -> DocumentTypes.AUDIO
            "TEXT" -> DocumentTypes.TEXT
            else -> DocumentTypes.ERROR
        }
    }

    @TypeConverter
    fun toString(value: DocumentTypes?): String? {
        return when (value){
            DocumentTypes.PICTURE -> "PICTURE"
            DocumentTypes.VIDEO -> "VIDEO"
            DocumentTypes.AUDIO -> "AUDIO"
            DocumentTypes.TEXT -> "TEXT"
            else -> "ERROR"
        }
    }
}