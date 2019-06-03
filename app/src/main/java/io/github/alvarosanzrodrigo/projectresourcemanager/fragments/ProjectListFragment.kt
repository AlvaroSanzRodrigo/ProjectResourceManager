package io.github.alvarosanzrodrigo.projectresourcemanager.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.alvarosanzrodrigo.projectresourcemanager.adapters.AdapterProjects
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.ProjectDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.viewModels.ProjectViewModel
import java.util.*
import kotlin.collections.ArrayList

class ProjectListFragment : Fragment() {
    private var items: ArrayList<Project> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //loadItems()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        //Ready our observable view model
        activity?.application?.let { ProjectDaoRepository.getInstance(it) }?.let {

            ViewModelProviders.of(this)
                .get(ProjectViewModel::class.java)
                .getAll(it).observe(this,
                    Observer<List<Project>> { list ->
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
}
