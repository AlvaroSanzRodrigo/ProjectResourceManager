package io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories

import android.app.Application
import android.arch.lifecycle.LiveData
import io.github.alvarosanzrodrigo.projectresourcemanager.database.AppDatabase
import io.github.alvarosanzrodrigo.projectresourcemanager.database.dao.ProjectDao
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project
import org.jetbrains.anko.doAsync

class ProjectDaoRepository internal constructor(application: Application) {

    private lateinit var mProjectDao: ProjectDao
    private lateinit var mProject: LiveData<List<Project>>

    companion object {
        @Volatile
        private var INSTANCE: ProjectDaoRepository? = null

        fun getInstance(context: Application): ProjectDaoRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ProjectDaoRepository(context).also { INSTANCE = it }
            }
    }

    init {
        AppDatabase.getInstance(application).also {
            mProjectDao = it.projectDao()
            mProject = mProjectDao.getAll()
        }
    }

    fun getAll(): LiveData<List<Project>> {
        return mProject
    }

    fun insertAll(projects: List<Project>) {

        doAsync {
            mProjectDao.insert(projects)
        }
    }

}