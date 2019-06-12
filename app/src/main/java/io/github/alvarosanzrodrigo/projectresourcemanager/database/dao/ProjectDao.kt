package io.github.alvarosanzrodrigo.projectresourcemanager.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project


@Dao
interface ProjectDao {


    @Query("SELECT * from project")
    fun getAll(): LiveData<List<Project>>


    @Query("DELETE FROM project")
    fun deleteAll()

    @Query("DELETE FROM project WHERE projectId == :projectId")
    fun deleteByProjectId(projectId: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(project: List<(Project)>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(project: Project): Long


    @Update
    fun update(project: Project): Int

}
