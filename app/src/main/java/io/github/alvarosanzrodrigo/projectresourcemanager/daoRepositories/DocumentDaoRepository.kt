package io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories

import android.app.Application
import android.arch.lifecycle.LiveData
import io.github.alvarosanzrodrigo.projectresourcemanager.database.AppDatabase
import io.github.alvarosanzrodrigo.projectresourcemanager.database.dao.DocumentDao
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document

import org.jetbrains.anko.doAsync

class DocumentDaoRepository internal constructor(application: Application) {

    private lateinit var mDocumentsDao: DocumentDao
    private lateinit var mDocument: LiveData<List<Document>>

    companion object {
        @Volatile
        private var INSTANCE: DocumentDaoRepository? = null

        fun getInstance(context: Application): DocumentDaoRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DocumentDaoRepository(context).also { INSTANCE = it }
            }
    }

    init {
        AppDatabase.getInstance(application).also {
            mDocumentsDao = it.documentDao()
            mDocument = mDocumentsDao.getAll()
        }
    }

    fun getAll(): LiveData<List<Document>> {
        return mDocument
    }

    fun getByProjectId(projectId : Int): LiveData<List<Document>> {
        return mDocumentsDao.getByProjectId(projectId)
    }

    fun getByProjectIdAndDocumentId(projectId : Int, documentId: Int): LiveData<List<Document>> {
        return mDocumentsDao.getByProjectIdAndDocumentId(projectId, documentId)
    }

    fun insertAll(documents: List<Document>) {

        doAsync {
            mDocumentsDao.insert(documents)
        }
    }

    fun update(document: Document) {
        doAsync {
            mDocumentsDao.update(document)
        }
    }

    fun deleteByDocumentId(documentId: Int, projectId: Int) {
        doAsync {
            mDocumentsDao.deleteByProjectIdAndDocumentI(documentId, projectId)
        }
    }

    fun deleteByProjectId(projectId: Int) {
        doAsync {
            mDocumentsDao.deleteByProjectId(projectId)
        }
    }

}