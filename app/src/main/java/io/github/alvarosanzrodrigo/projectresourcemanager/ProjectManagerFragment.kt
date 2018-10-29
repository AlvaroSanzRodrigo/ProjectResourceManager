package io.github.alvarosanzrodrigo.projectresourcemanager

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.alvarosanzrodrigo.projectresourcemanager.Models.Document
import java.util.*


class ProjectManagerFragment : Fragment() {
    lateinit var items: ArrayList<Document>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_manager, container, false)
    }


}
