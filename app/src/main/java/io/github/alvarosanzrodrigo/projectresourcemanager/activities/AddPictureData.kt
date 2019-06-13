package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.enums.DocumentTypes
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.viewModels.DocumentViewModel
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.util.Date


class AddPictureData : AppCompatActivity() {

    private lateinit var picturePath: String
    var projectId: Int = 0
    var documentId: Int = 0
    private lateinit var documentToUpdate: Document
    private lateinit var imageView: ImageView
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var notes: EditText
    private lateinit var save: Button
    private lateinit var cancel: Button

    private fun forEdit() {
        if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true) {
            documentId = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_ID) as Int
            loadData()
        }
    }

    private fun loadData() {
        println("hola?")
        println("DOCUMENT ID -> $documentId")
        println("PROJECT ID -> $projectId")
        application?.let { DocumentDaoRepository.getInstance(it) }?.let {

            ViewModelProviders.of(this)
                .get(DocumentViewModel::class.java)
                .getByProjectIdAndDocumentId(it, projectId, documentId).observe(this,
                    Observer<List<Document>> { list ->
                        if (list != null) {
                            for (item in list) {
                                documentToUpdate = item
                                picturePath = item.path
                                Picasso.get()
                                    .load("file://$picturePath")
                                    .placeholder(R.drawable.ic_image_placeholder)
                                    .error(R.drawable.ic_image_error)
                                    .into(imageView)
                                title.text.insert(0, item.title)
                                description.text.insert(0, item.description)
                                notes.text.insert(0, item.notes)
                            }
                        }
                    }
                )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) != true) {
            toast("Picture cancelled")
            val deleteFile = File(picturePath)
            deleteFile.delete()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        picturePath = intent?.extras?.get(ProjectDocumentsManagerFragment.IMAGE_PATH) as String
        projectId = intent?.extras?.get(ProjectDocumentsManagerFragment.PROJECT_ID) as Int

        setContentView(R.layout.activity_add_picture_data)

        bindView()
        forEdit()

        Picasso.get()
            .load("file://$picturePath")
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(imageView)

    }

    private fun savePicture() {
        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val picture = listOf(
            Document(
                projectId,
                validatedTitle,
                picturePath,
                notes.text.toString(),
                listOf(""),
                description.text.toString(),
                Date(),
                DocumentTypes.PICTURE
            )
        )
        println(picture)
        application?.let { DocumentDaoRepository.getInstance(it).insertAll(picture) }
    }

    private fun updatePicture() {
        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val pictureToUpdate = Document(
            projectId,
            validatedTitle,
            picturePath,
            notes.text.toString(),
            listOf(""),
            description.text.toString(),
            Date(),
            DocumentTypes.PICTURE
        )
        pictureToUpdate.documentId = documentId
        application?.let { DocumentDaoRepository.getInstance(it).update(pictureToUpdate) }
    }

    private fun bindView() {
        imageView = findViewById(R.id.add_picture_data_image)
        title = findViewById(R.id.add_picture_data_title_text)
        description = findViewById(R.id.add_picture_data_description_text)
        notes = findViewById(R.id.add_picture_data_notes_text)
        save = findViewById(R.id.save_image_data_button)
        cancel = findViewById(R.id.cancel_action_image_data_button)

        save.setOnClickListener {

            if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true) {
                updatePicture()
            } else {
                savePicture()
            }

            this.finish()
        }
        cancel.setOnClickListener {

            if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true) {
                this.finish()
            } else {
                println("attempting to delete")
                val deleteFile = File(picturePath)
                deleteFile.delete()
                this.finish()
            }

        }
    }
}
