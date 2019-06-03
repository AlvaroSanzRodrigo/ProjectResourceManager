package io.github.alvarosanzrodrigo.projectresourcemanager.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.ProjectDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project
import kotlinx.android.synthetic.main.fragment_dialog_new_project.view.*
import java.util.*

class NewProjectDialogFragment : DialogFragment(){

    private lateinit var newProjectName: EditText
    private lateinit var saveButton: Button




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_dialog_new_project, container, false).apply {
            newProjectName = findViewById(R.id.project_name_edit_text)
            saveButton = findViewById(R.id.save_project_name_button)
            saveButton.setOnClickListener {
                if (newProjectName.text.toString() != ""){
                    val projects = listOf(Project(newProjectName.text.toString(), date = Date()))
                    activity?.application?.let { ProjectDaoRepository.getInstance(it).insertAll(projects) }
                }
                this@NewProjectDialogFragment.dismiss()
            }
        }
    }
}
