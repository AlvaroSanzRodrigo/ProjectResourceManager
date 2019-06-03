package io.github.alvarosanzrodrigo.projectresourcemanager.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
    entity = Project::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("projectId")
)]
)
class Document(
    var projectId: Int,
    //var image: Drawable,
    var date: Date,
    var type: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}