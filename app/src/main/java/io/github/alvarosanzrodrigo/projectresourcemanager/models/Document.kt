package io.github.alvarosanzrodrigo.projectresourcemanager.models

import android.arch.persistence.room.*
import io.github.alvarosanzrodrigo.projectresourcemanager.database.ListConverter
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
    //var image: Drawable,
    var path: String,
    /*
    @Ignore
    @TypeConverters(ListConverter::class)
    var tags:List<String>,
    */
    var description: String,
    var date: Date,
    var type: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}