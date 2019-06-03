package io.github.alvarosanzrodrigo.projectresourcemanager.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*
import kotlin.collections.HashMap

@Entity
data class Project(
    var title: String,
    var date: Date){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    //This is ignored in the database but will be filled
    @Ignore var documents: HashMap<Int, Document> = HashMap()
}