package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.SureToDeleteDialogFragment
import java.io.File

class ViewVideoData : AppCompatActivity(), SureToDeleteDialogFragment.OnClickedOptionListener {

    override fun onOptionChoosed(optionChoosed: Int) {
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

    private lateinit var videoView: VideoView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var notesView: TextView
    private lateinit var edit: Button
    private lateinit var cancel: Button
    private lateinit var delete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_video_data)
        getExtras()
        bindView()
        loadData()

    }

    private fun getExtras() {
        path = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_PATH) as String
        title = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_TITLE) as String
        notes = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_NOTES) as String
        description = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_DESCRIPTION) as String
        projectId = intent?.extras?.get(ProjectDocumentsManagerFragment.PROJECT_ID) as Int
        documentId = intent?.extras?.get(ProjectDocumentsManagerFragment.DOCUMENT_ID) as Int
    }

    private fun bindView() {
        videoView = findViewById(R.id.videoView_view_video_data)
        titleView = findViewById(R.id.view_video_data_title)
        descriptionView = findViewById(R.id.view_video_data_description)
        notesView = findViewById(R.id.view_video_data_notes)
        edit = findViewById(R.id.edit_video_data_button)
        cancel = findViewById(R.id.cancel_action_video_view_button)
        delete = findViewById(R.id.delete_video_view_button)

        edit.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(ProjectDocumentsManagerFragment.PROJECT_NAME, "")
            bundle.putString(ProjectDocumentsManagerFragment.IMAGE_PATH, "")
            bundle.putString(ProjectDocumentsManagerFragment.VIDEO_URI, "")
            bundle.putInt(ProjectDocumentsManagerFragment.PROJECT_ID, projectId)
            bundle.putInt(ProjectDocumentsManagerFragment.DOCUMENT_ID, documentId)
            bundle.putBoolean(ProjectDocumentsManagerFragment.EDIT, true)
            val addDataIntent = Intent(this, AddVideoData::class.java)
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

    private fun loadData() {
        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        videoView.setVideoPath(path)
        videoView.start()
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
