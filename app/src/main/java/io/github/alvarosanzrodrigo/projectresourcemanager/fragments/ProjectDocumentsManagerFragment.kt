package io.github.alvarosanzrodrigo.projectresourcemanager.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout

import io.github.alvarosanzrodrigo.projectresourcemanager.R
import java.util.*
import kotlin.collections.ArrayList
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.Toast
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Picture
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.IOException
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.app.ActivityCompat
import com.robertlevonyan.components.picker.ItemModel
import com.robertlevonyan.components.picker.PickerDialog
import io.github.alvarosanzrodrigo.projectresourcemanager.adapters.AdapterDocument
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URI
import java.text.SimpleDateFormat


class ProjectDocumentsManagerFragment : Fragment() {
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


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            //loadItems(it)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        println("AL MENOS PASA PRO EL PRICIPIO DE CREATE IMAGE FILE")
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalStorageDirectory()

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            println("?????")
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun sendTakePictureIntent() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true)
        if (cameraIntent.resolveActivity(context!!.packageManager) != null) {
            startActivityForResult(cameraIntent, 1)

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
                startActivityForResult(cameraIntent, 1)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun savePicture(uri: Uri) {
        val picture = File(uri.path).createNewFile()
        val savedPicturePath = File(Environment.getExternalStorageDirectory().path + "/ProjectResourceManager/"+projectName+"/pics/")
        savedPicturePath.mkdirs()
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            savedPicturePath
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
        //val out = this.context?.contentResolver?.openOutputStream(uri)
        //this.context?.contentResolver?.openInputStream(uri)
        Toast.makeText(this.context, projectName, Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        projectId = arguments?.get("projectId") as Int
        projectName = arguments?.get("projectName") as String

        val pickerDialog = PickerDialog.Builder(this@ProjectDocumentsManagerFragment)// Activity or Fragment
            .setTitle("Choose from")
            .setItems(ArrayList(listOf(ItemModel(ItemModel.ITEM_CAMERA), ItemModel(ItemModel.ITEM_GALLERY))))
            .setDialogStyle(PickerDialog.DIALOG_MATERIAL)
            .create()

        pickerDialog.setPickerCloseListener { type, uri ->
            when (type) {
                ItemModel.ITEM_CAMERA -> savePicture(uri)/* do something with the photo you've taken */
                ItemModel.ITEM_GALLERY -> savePicture(uri)/* do something with the image you've chosen */
            }
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
                pickerDialog.show()
                //sendTakePictureIntent()
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
