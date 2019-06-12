package io.github.alvarosanzrodrigo.projectresourcemanager.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.DocumentDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.ProjectDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.database.dao.ProjectDao
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project

class DocumentViewModel : ViewModel() {
    private var mDocumentLiveData: LiveData<List<Document>>? = null
    fun getAll(documentDaoRepository: DocumentDaoRepository): LiveData<List<Document>> {
        if (mDocumentLiveData == null) {
            mDocumentLiveData = documentDaoRepository.getAll()
        }
        return mDocumentLiveData!!
    }

    fun getByProjectId(documentDaoRepository: DocumentDaoRepository, projectId: Int): LiveData<List<Document>> {
        if (mDocumentLiveData == null) {
            mDocumentLiveData = documentDaoRepository.getByProjectId(projectId)
        }
        return mDocumentLiveData!!
    }
}