package io.github.alvarosanzrodrigo.projectresourcemanager.models

import android.arch.persistence.room.*
import io.github.alvarosanzrodrigo.projectresourcemanager.database.DocumentTypeConverter
import io.github.alvarosanzrodrigo.projectresourcemanager.database.ListConverter
import io.github.alvarosanzrodrigo.projectresourcemanager.enums.DocumentTypes
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
    entity = Project::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("projectId")
)]
)
data class Document(
    var projectId: Int,
    var title: String,
    var path: String,
    var notes: String,
    @TypeConverters(ListConverter::class)
    var tags:List<String>,
    var description: String,
    var date: Date,
    @TypeConverters(DocumentTypeConverter::class)
    var type: DocumentTypes
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}