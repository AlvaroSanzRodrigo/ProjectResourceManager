package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.keenfin.audioview.AudioView
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.enums.DocumentTypes
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.viewModels.DocumentViewModel
import java.io.File
import java.util.*


class AddAudioData : AppCompatActivity() {

    private lateinit var audioPath: String
    var projectId: Int = 0
    var documentId: Int = 0
    private lateinit var documentToUpdate: Document

    private lateinit var audioView: AudioView
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
                                audioPath = item.path
                                title.text.insert(0, item.title)
                                description.text.insert(0, item.description)
                                notes.text.insert(0, item.notes)

                                audioView.setDataSource(item.path)
                            }
                        }
                    }
                )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_audio_data)

        audioPath = intent?.extras?.get(ProjectDocumentsManagerFragment.AUDIO_PATH) as String
        projectId = intent?.extras?.get(ProjectDocumentsManagerFragment.PROJECT_ID) as Int

        bindView()
        forEdit()

        audioView.setDataSource(audioPath)
        audioView.start()
    }

    private fun saveAudio() {
        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val audio = listOf(
            Document(
                projectId,
                validatedTitle,
                audioPath,
                notes.text.toString(),
                listOf(""),
                description.text.toString(),
                Date(),
                DocumentTypes.AUDIO
            )
        )
        println(audio)
        application?.let { DocumentDaoRepository.getInstance(it).insertAll(audio) }
    }

    private fun updateAudio() {
        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val audio = Document(
            projectId,
            validatedTitle,
            audioPath,
            notes.text.toString(),
            listOf(""),
            description.text.toString(),
            Date(),
            DocumentTypes.AUDIO
        )
        audio.documentId = documentId
        application?.let { DocumentDaoRepository.getInstance(it).update(audio) }

    }

    private fun bindView() {
        audioView = findViewById(R.id.add_audio_data_audioView)
        title = findViewById(R.id.add_audio_data_title_text)
        description = findViewById(R.id.add_audio_data_description_text)
        notes = findViewById(R.id.add_audio_data_notes_text)
        save = findViewById(R.id.save_audio_data_button)
        cancel = findViewById(R.id.cancel_audio_data_button)

        save.setOnClickListener {
            if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true) {
                updateAudio()
            } else {
                saveAudio()
            }
            this.finish()
        }
        cancel.setOnClickListener {
            if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true) {
                this.finish()
            }
            else {
                println("attempting to delete")
                val deleteFile = File(audioPath)
                deleteFile.delete()
                this.finish()
            }
        }
    }

}
