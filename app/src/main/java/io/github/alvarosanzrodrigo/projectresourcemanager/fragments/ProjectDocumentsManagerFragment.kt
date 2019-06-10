package io.github.alvarosanzrodrigo.projectresourcemanager.Fragments

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.activities.AddPictureData
import io.github.alvarosanzrodrigo.projectresourcemanager.adapters.AdapterDocument
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.CameraOrGalleryDialogFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.viewModels.DocumentViewModel
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList


class ProjectDocumentsManagerFragment : Fragment(), CameraOrGalleryDialogFragment.OnClickedOptionListener {

    companion object {
        const val  IMAGE_PATH = "IMAGE_PATH"
        const val  PROJECT_ID = "PROJECT_ID"
        private const val REQUEST_VIDEO_CAPTURE = 3
    }

    override fun onOptionChoosed(optionChoosed: Int) {
        if (isPictureChoosed) {
            when (optionChoosed) {
                1 -> sendTakePictureIntent()
                2 -> sendGalleryPictureIntent()
            }
        } else {
            when (optionChoosed) {
                1 -> dispatchTakeVideoIntent()
                2 -> sendGalleryVideoIntent()
            }
        }

    }

    private var items: ArrayList<Document> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var morph: FABToolbarLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var toolbarImageViewText: ImageView
    private lateinit var toolbarImageViewAudio: ImageView
    private lateinit var toolbarImageViewVideo: ImageView
    private lateinit var toolbarImageViewPhoto: ImageView
    private lateinit var pictureFilePath: String

    private var projectId: Int = 0
    private var projectName: String = ""
    private lateinit var currentPhotoPath: String
    private var isPictureChoosed: Boolean = true


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        println("AL MENOS PASA PRO EL PRICIPIO DE CREATE IMAGE FILE")
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            File(Environment.getExternalStorageDirectory().path + "/ProjectResourceManager/" + projectName + "/pics/")

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    @Throws(IOException::class)
    private fun createVideoFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            File(Environment.getExternalStorageDirectory().path + "/ProjectResourceManager/" + projectName + "/video/")

        return File.createTempFile(
            "MP4_${timeStamp}_", /* prefix */
            ".mp4", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(context!!.packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }

    private fun sendGalleryPictureIntent() {

        val cameraIntent = Intent(Intent.ACTION_GET_CONTENT)
        cameraIntent.type = "image/*"
        startActivityForResult(Intent.createChooser(cameraIntent, "Select Picture"), 2)
    }

    private fun sendGalleryVideoIntent() {

        val cameraIntent = Intent(Intent.ACTION_GET_CONTENT)
        cameraIntent.type = "video/*"
        startActivityForResult(Intent.createChooser(cameraIntent, "Select Picture"), REQUEST_VIDEO_CAPTURE)
    }


    private fun sendTakePictureIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true)
        if (cameraIntent.resolveActivity(context!!.packageManager) != null) {

            var pictureFile: File?
            try {
                pictureFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                Toast.makeText(
                    context,
                    "Photo file can't be created, please try again",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            if (pictureFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    context!!,
                    "io.github.alvarosanzrodrigo.projectresourcemanager",
                    pictureFile
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            }
            startActivityForResult(cameraIntent, 1)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //here goes the intent to go to the picture info form :)
            var bundle = Bundle()
            bundle.putString(IMAGE_PATH, currentPhotoPath)
            bundle.putInt(PROJECT_ID, projectId)
            val addPictureDataIntent = Intent(activity,AddPictureData::class.java)
            addPictureDataIntent.putExtras(bundle)
            startActivity(addPictureDataIntent)

        } else if (requestCode == 2 && resultCode == RESULT_OK) {

            var imageBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data?.data)

            var pictureFile: File?
            try {
                pictureFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                Toast.makeText(
                    context,
                    "Photo file can't be created, please try again",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            if (pictureFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    context!!,
                    "io.github.alvarosanzrodrigo.projectresourcemanager",
                    pictureFile
                )
            }

            try {
                val out =  FileOutputStream(pictureFile)
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            } catch (e: IOException ) {
                e.printStackTrace()
            }

             //here goes the intent to go to the picture info form :)
            var bundle = Bundle()
            bundle.putString(IMAGE_PATH, currentPhotoPath)
            bundle.putInt(PROJECT_ID, projectId)
            val addPictureDataIntent = Intent(activity,AddPictureData::class.java)
            addPictureDataIntent.putExtras(bundle)
            startActivity(addPictureDataIntent)
        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri: Uri = data?.data!!
            val videoFile = createVideoFile()
            //val videoSource = File()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectId = it.get("projectId") as Int
            projectName = it.get("projectName") as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Ready our observable view model
        activity?.application?.let { DocumentDaoRepository.getInstance(it) }?.let {

            ViewModelProviders.of(this)
                .get(DocumentViewModel::class.java)
                .getAll(it).observe(
                    this,
                    Observer { list ->
                        if (list != null) {
                            items.clear()
                            for (item in list) {
                                items.add(item)
                            }
                        }
                        viewAdapter.notifyDataSetChanged()
                    }
                )
        }

        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_project_manager, container, false)
        morph = rootView.findViewById(R.id.fabtoolbar)
        fab = rootView.findViewById<FloatingActionButton>(R.id.fabtoolbar_fab).apply {
            this.setOnClickListener {
                morph.show()
            }
        }
        toolbarImageViewAudio = rootView.findViewById<ImageView>(R.id.toolbar_audio).apply {
            this.setOnClickListener {

                morph.hide()
            }
        }
        toolbarImageViewPhoto = rootView.findViewById<ImageView>(R.id.toolbar_image).apply {
            this.setOnClickListener {
                isPictureChoosed = true
                val chooser = CameraOrGalleryDialogFragment()
                chooser.mCallBack = this@ProjectDocumentsManagerFragment
                chooser.show(fragmentManager, "chooser")
                morph.hide()
            }
        }
        toolbarImageViewText = rootView.findViewById<ImageView>(R.id.toolbar_text).apply {
            this.setOnClickListener {
                morph.hide()
            }
        }
        toolbarImageViewVideo = rootView.findViewById<ImageView>(R.id.toolbar_video).apply {
            this.setOnClickListener {
                isPictureChoosed = false
                val chooser = CameraOrGalleryDialogFragment()
                chooser.mCallBack = this@ProjectDocumentsManagerFragment
                chooser.show(fragmentManager, "chooser")
                morph.hide()
            }
        }
        viewManager = LinearLayoutManager(activity)

        //here is where I need to do all the LiveData things, one retrieved the project and the project name
        viewAdapter = AdapterDocument(items)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.rv_documents).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return rootView
    }

    /*
    private fun loadItems(context: Context) {
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_image
        )!!, Date(), "Imagen"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_audio
        )!!, Date(), "Audio"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_video
        )!!, Date(), "Video"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_text
        )!!, Date(), "Text"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_image
        )!!, Date(), "Imagen"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_audio
        )!!, Date(), "Audio"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_video
        )!!, Date(), "Video"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_text
        )!!, Date(), "Text"))

        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_image
        )!!, Date(), "Imagen"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_audio
        )!!, Date(), "Audio"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_video
        )!!, Date(), "Video"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_text
        )!!, Date(), "Text"))

        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_image
        )!!, Date(), "Imagen"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_audio
        )!!, Date(), "Audio"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_video
        )!!, Date(), "Video"))
        items.add(Document(ContextCompat.getDrawable(context,
            R.drawable.ic_text
        )!!, Date(), "Text"))
    }
    */
}
