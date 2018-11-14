package io.github.alvarosanzrodrigo.projectresourcemanager.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.alvarosanzrodrigo.projectresourcemanager.Adapters.AdapterProjects
import io.github.alvarosanzrodrigo.projectresourcemanager.Models.Project
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import java.util.*

class ProjectListFragment : Fragment() {
    private var items: ArrayList<Project> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loadItems()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_project_list, container, false)
        viewManager = LinearLayoutManager(activity)
        viewAdapter = AdapterProjects(items)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.rv_projects).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return rootView
    }

    private fun loadItems(){
        context?.let { it ->
            ContextCompat.getDrawable(it, R.drawable.ic_folder)?.let { Project("Projecto Alfa", Date(), it) }?.let {
                items.add(
                    it
                )
            }
        }
        context?.let { it ->
            ContextCompat.getDrawable(it, R.drawable.ic_folder)?.let { Project("Projecto Beta", Date(), it) }?.let {
                items.add(
                    it
                )
            }
        }
        context?.let { it ->
            ContextCompat.getDrawable(it, R.drawable.ic_folder)?.let { Project("Projecto Gamma", Date(), it) }?.let {
                items.add(
                    it
                )
            }
        }
    }
}
