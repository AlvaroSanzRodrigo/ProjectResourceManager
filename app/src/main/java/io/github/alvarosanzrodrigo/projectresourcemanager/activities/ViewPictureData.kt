package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.SureToDeleteDialogFragment
import java.io.File

class ViewPictureData : AppCompatActivity(), SureToDeleteDialogFragment.OnClickedOptionListener {
    override fun onOptionChoosedDeleted(optionChoosed: Int) {
        when (optionChoosed) {
            1 -> deleteData()
        }
    }

    private var projectId: Int = 0
    private var documentId: Int = 0
    private lateinit var title: String
    private lateinit var path: String
    private lateinit var description: String
    private lateinit var notes: String

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var notesView: TextView
    private lateinit var edit: Button
    private lateinit var cancel: Button
    private lateinit var delete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_picture_data)

        getExtras()
        bindView()
        loadData()

    }

    private fun getExtras(){
        path = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_PATH) as String
        title = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_TITLE) as String
        notes = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_NOTES) as String
        description = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_DESCRIPTION) as String
        projectId = intent?.extras?.get(ProjectDocumentsManagerFragment.PROJECT_ID) as Int
        documentId = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_ID) as Int
    }

    private fun bindView(){
        imageView = findViewById(R.id.view_picture_data_image)
        titleView = findViewById(R.id.view_picture_data_title)
        descriptionView = findViewById(R.id.view_picture_data_description)
        notesView = findViewById(R.id.view_picture_data_notes)
        edit = findViewById(R.id.edit_image_data_button)
        cancel = findViewById(R.id.cancel_action_image_view_button)
        delete = findViewById(R.id.delete_image_view_button)

        edit.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(ProjectDocumentsManagerFragment.PROJECT_NAME, "")
            bundle.putInt(ProjectDocumentsManagerFragment.PROJECT_ID, projectId)
            bundle.putInt(ProjectDocumentsManagerFragment.DOCUMENT_ID, documentId)
            bundle.putString(ProjectDocumentsManagerFragment.IMAGE_PATH, "")
            bundle.putBoolean(ProjectDocumentsManagerFragment.EDIT, true)
            val addDataIntent = Intent(this, AddPictureData::class.java)
            addDataIntent.putExtras(bundle)
            startActivity(addDataIntent)
            this.finish()
        }

        cancel.setOnClickListener {
            this.finish()
        }

        delete.setOnClickListener {
            sendDeleteDialog()
        }
    }

    private fun loadData(){
        Picasso.get()
            .load("file://$path")
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(imageView)

        titleView.text = title
        descriptionView.text = description
        notesView.text = notes
    }

    private fun sendDeleteDialog(){
        val chooser = SureToDeleteDialogFragment()
        chooser.mCallBack = this
        chooser.show(supportFragmentManager, "chooser")
    }

    private fun deleteData(){
        DocumentDaoRepository.getInstance(application).deleteByDocumentId(documentId, projectId)
        val fileToDelete = File(path)
        fileToDelete.delete()
        this.finish()
    }

}
