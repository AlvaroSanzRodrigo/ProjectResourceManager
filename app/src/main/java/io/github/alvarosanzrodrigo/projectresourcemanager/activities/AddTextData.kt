package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.ProjectDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.enums.DocumentTypes
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project
import io.github.alvarosanzrodrigo.projectresourcemanager.viewModels.DocumentViewModel
import io.github.alvarosanzrodrigo.projectresourcemanager.viewModels.ProjectViewModel
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class AddTextData : AppCompatActivity() {

    var projectId: Int = 0
    var documentId: Int = 0
    private lateinit var projectName: String
    private lateinit var documentToUpdate: Document
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var notes: EditText
    private lateinit var text: EditText
    private lateinit var save: Button
    private lateinit var cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_text_data)
        projectId = intent?.extras?.get(ProjectDocumentsManagerFragment.PROJECT_ID) as Int
        projectName = intent?.extras?.get(ProjectDocumentsManagerFragment.PROJECT_NAME) as String

        bindView()
        forEdit()
    }

    private fun forEdit() {
        if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true){
            documentId = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_ID) as Int
            loadData()
        }
    }

    private fun loadData(){
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
                                text.text.insert(0, FileInputStream(File(item.path)).bufferedReader().use { reader -> reader.readText() })
                                title.text.insert(0, item.title)
                                description.text.insert(0, item.description)
                                notes.text.insert(0, item.notes)
                            }
                        }
                    }
                )
        }
    }

    private fun saveText() {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val textFile = File(Environment.getExternalStorageDirectory().path + "/ProjectResourceManager/" + projectName + "/text/TXT_$timeStamp" + ".txt")

        FileOutputStream(textFile).use {
            it.write(text.text.toString().toByteArray())
        }

        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val text = listOf(Document(projectId, validatedTitle, textFile.absolutePath, notes.text.toString(), listOf(""), description.text.toString(), Date(), DocumentTypes.TEXT))
        println(text)
        application?.let { DocumentDaoRepository.getInstance(it).insertAll(text) }
    }

    private fun bindView(){
        text = findViewById(R.id.add_text_mtext_data_text)
        title = findViewById(R.id.add_text_data_title_text)
        description = findViewById(R.id.add_text_data_description_text)
        notes = findViewById(R.id.add_text_data_notes_text)
        save = findViewById(R.id.save_text_data_button)
        cancel = findViewById(R.id.cancel_text_image_data_button)

        save.setOnClickListener {
            if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true){
                editText()
            }else{
                saveText()
            }
            this.finish()
        }
        cancel.setOnClickListener {
            this.finish()
        }
    }

    private fun editText() {
        FileOutputStream(File(documentToUpdate.path)).use {
            it.write(text.text.toString().toByteArray())
        }

        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val updatedDocument = Document(projectId, validatedTitle, documentToUpdate.path, notes.text.toString(), listOf(""), description.text.toString(), Date(), DocumentTypes.TEXT)
        updatedDocument.documentId = documentToUpdate.documentId
        application?.let { DocumentDaoRepository.getInstance(it).update(updatedDocument) }
    }
}
