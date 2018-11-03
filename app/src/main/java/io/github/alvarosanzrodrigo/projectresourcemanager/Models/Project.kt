package io.github.alvarosanzrodrigo.projectresourcemanager.Models

import java.util.*
import kotlin.collections.HashMap

class Project(var title: String, var date: Date, var documents: HashMap<Int, Document>)