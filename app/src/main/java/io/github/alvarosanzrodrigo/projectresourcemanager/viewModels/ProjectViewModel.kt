package io.github.alvarosanzrodrigo.projectresourcemanager.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.ProjectDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.database.dao.ProjectDao
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project

class ProjectViewModel : ViewModel() {
    private var mProjectLiveData: LiveData<List<Project>>? = null
    fun getAll(projectDaoRepository: ProjectDaoRepository): LiveData<List<Project>> {
        if (mProjectLiveData == null) {
            mProjectLiveData = projectDaoRepository.getAll()
        }
        return mProjectLiveData!!
    }
}