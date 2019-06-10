package io.github.alvarosanzrodrigo.projectresourcemanager.database


import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import io.github.alvarosanzrodrigo.projectresourcemanager.database.dao.DocumentDao
import io.github.alvarosanzrodrigo.projectresourcemanager.database.dao.ProjectDao
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project
import org.jetbrains.anko.doAsync

@Database(
    entities = [Project::class, Document::class],
    version = 6
)

@TypeConverters(DataConverter::class, ListConverter::class, DocumentTypeConverter::class)


abstract class AppDatabase : RoomDatabase() {

    abstract fun documentDao(): DocumentDao
    abstract fun projectDao(): ProjectDao



    companion object {
        // Get DB name from constant configuration
        private val DB_NAME = "prm_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Application): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        fun destroyInstance() {
            INSTANCE = null
        }


        private fun buildDatabase(context: Application) =
            // ONLY FOR TEST. Change to databaseBuilder()
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                // Wipes and rebuilds instead of migrating
                // if no Migration object.
                .fallbackToDestructiveMigration()
                .addCallback(sRoomDatabaseCallback)

                .build()

        //----------------------------------CALLBACKS--------------------------------------------------------------------//
        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }

    }
}








