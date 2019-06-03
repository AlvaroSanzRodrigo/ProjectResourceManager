package io.github.alvarosanzrodrigo.projectresourcemanager.fragments

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.ProjectDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

class NewProjectDialogFragment : DialogFragment(){

    private lateinit var newProjectName: EditText
    private lateinit var saveButton: Button




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_dialog_new_project, container, false).apply {
            newProjectName = findViewById(R.id.project_name_edit_text)
            saveButton = findViewById(R.id.save_project_name_button)
            saveButton.setOnClickListener {
                val projectName = newProjectName.text.toString()
                if (projectName != ""){
                    val projects = listOf(Project(projectName, date = Date()))
                    // create a File object for the parent directory
                    val projectDirectoryPath = Environment.getExternalStorageDirectory().path + "/ProjectResourceManager/"+projectName+"/"
                    val projectDirectory = File(projectDirectoryPath)
                    val picsDirectory = File(projectDirectoryPath+"pics")
                    val videoDirectory = File(projectDirectoryPath+"video")
                    val audioDirectory = File(projectDirectoryPath+"audio")
                    val textDirectory = File(projectDirectoryPath+"text")
                    // have the object build the directory structure, if needed.
                    projectDirectory.mkdirs()
                    picsDirectory.mkdirs()
                    videoDirectory.mkdirs()
                    audioDirectory.mkdirs()
                    textDirectory.mkdirs()

                    activity?.application?.let { ProjectDaoRepository.getInstance(it).insertAll(projects) }
                }
                this@NewProjectDialogFragment.dismiss()
            }
        }
    }
}
