package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R

class AddPictureData : AppCompatActivity() {

    lateinit var picturePath: String

    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        picturePath = intent?.extras?.get(ProjectDocumentsManagerFragment.IMAGE_PATH) as String

        setContentView(R.layout.activity_add_picture_data)

        imageView = findViewById(R.id.add_picture_data_image)

        Picasso.get()
            .load("file://$picturePath")
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(imageView)

    }
}
