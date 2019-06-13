package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.MediaController
import android.widget.VideoView
import com.squareup.picasso.Picasso
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.enums.DocumentTypes
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.viewModels.DocumentViewModel
import org.jetbrains.anko.toast
import java.io.File
import java.util.Date

class AddVideoData : AppCompatActivity() {

    private lateinit var videoPath: String
    private lateinit var documentToUpdate: Document
    var projectId: Int = 0
    private var documentId: Int = 0

    private lateinit var videoUri: Uri
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var notes: EditText
    private lateinit var save: Button
    private lateinit var cancel: Button

    private lateinit var videoView: VideoView

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
                                videoPath = item.path
                                videoView.setVideoPath(item.path)
                                title.text.insert(0, item.title)
                                description.text.insert(0, item.description)
                                notes.text.insert(0, item.notes)
                                videoView.start()
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
            val deleteFile = File(videoPath)
            deleteFile.delete()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_video_data)

        bindView()

        videoPath = intent?.extras?.get(ProjectDocumentsManagerFragment.IMAGE_PATH) as String
        projectId = intent?.extras?.get(ProjectDocumentsManagerFragment.PROJECT_ID) as Int
        val videoUriString = intent?.extras?.get(ProjectDocumentsManagerFragment.VIDEO_URI) as String
        videoUri = Uri.parse(videoUriString)


        val mediaController = MediaController(this)

        videoView.setMediaController(mediaController)

        if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) != true) {
            videoView.setVideoURI(videoUri)
            videoView.start()
        }

        forEdit()
    }

    private fun savePicture() {
        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val video = listOf(
            Document(
                projectId,
                validatedTitle,
                videoPath,
                notes.text.toString(),
                listOf(""),
                description.text.toString(),
                Date(),
                DocumentTypes.VIDEO
            )
        )
        application?.let { DocumentDaoRepository.getInstance(it).insertAll(video) }
    }

    private fun updateVideo() {
        val validatedTitle = if (title.text.isEmpty()) "No Title" else title.text.toString()
        val video = Document(
            projectId,
            validatedTitle,
            videoPath,
            notes.text.toString(),
            listOf(""),
            description.text.toString(),
            Date(),
            DocumentTypes.VIDEO
        )
        video.documentId = documentId
        application?.let { DocumentDaoRepository.getInstance(it).update(video) }
    }

    private fun bindView() {
        videoView = findViewById(R.id.videoView_add_video_data)
        title = findViewById(R.id.add_video_data_title_text)
        description = findViewById(R.id.add_video_data_description_text)
        notes = findViewById(R.id.add_video_data_notes_text)
        save = findViewById(R.id.save_video_data_button)
        cancel = findViewById(R.id.cancel_video_image_data_button)

        save.setOnClickListener {
            if (intent?.extras?.get(ProjectDocumentsManagerFragment.EDIT) == true) {
                updateVideo()
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
                val deleteFile = File(videoPath)
                deleteFile.delete()
                this.finish()
            }
        }
    }
}
