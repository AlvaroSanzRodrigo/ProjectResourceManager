package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import java.io.File

class AddTextData : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var notes: EditText
    private lateinit var text: EditText
    private lateinit var save: Button
    private lateinit var cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_text_data)
    }

    private fun bindView(){
        text = findViewById(R.id.add_text_mtext_data_description_text)
        title = findViewById(R.id.add_text_data_description_text)
        description = findViewById(R.id.add_text_data_description_text)
        notes = findViewById(R.id.add_text_data_notes_text)
        save = findViewById(R.id.save_text_data_button)
        cancel = findViewById(R.id.cancel_text_image_data_button)

        save.setOnClickListener {
            //savePicture()
            this.finish()
        }
        cancel.setOnClickListener {
            this.finish()
        }
    }
}
