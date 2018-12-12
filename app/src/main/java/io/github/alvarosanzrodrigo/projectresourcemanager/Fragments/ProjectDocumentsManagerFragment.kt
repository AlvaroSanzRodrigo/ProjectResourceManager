package io.github.alvarosanzrodrigo.projectresourcemanager.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.alvarosanzrodrigo.projectresourcemanager.Models.Document
import java.util.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlin.collections.ArrayList
import io.github.alvarosanzrodrigo.projectresourcemanager.Adapters.AdapterDocument
import io.github.alvarosanzrodrigo.projectresourcemanager.R


class ProjectDocumentsManagerFragment : Fragment() {
    private var items: ArrayList<Document> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            loadItems(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_project_manager, container, false)
        viewManager = LinearLayoutManager(activity)
        viewAdapter = AdapterDocument(items)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.rv_documents).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return rootView
    }

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

}
