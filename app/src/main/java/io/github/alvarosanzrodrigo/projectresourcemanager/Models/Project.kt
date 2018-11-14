package io.github.alvarosanzrodrigo.projectresourcemanager.Models

import android.app.Application
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import java.util.*
import kotlin.collections.HashMap

class Project(var title: String, var date: Date, val drawable: Drawable){
    var documents: HashMap<Int, Document> = HashMap()

}