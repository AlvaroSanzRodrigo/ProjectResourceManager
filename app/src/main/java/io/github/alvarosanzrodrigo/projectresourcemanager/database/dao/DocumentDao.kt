package io.github.alvarosanzrodrigo.projectresourcemanager.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document


@Dao
interface DocumentDao {


    @Query("SELECT * from document")
    fun getAll(): LiveData<List<Document>>


    @Query("DELETE FROM document ")
    fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(document: List<Document>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(document: Document): Long


    @Update
    fun update(document: Document): Int

}
