package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesapp.database.dao.NotesListDao
import com.example.notesapp.database.dbmodel.NotesDBModel

/**
 * room database class
 */
@Database(entities = [NotesDBModel::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class NotesAppRoomDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: NotesAppRoomDatabase? = null

        fun getInstance(context: Context): NotesAppRoomDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, NotesAppRoomDatabase::class.java, DB_NAME)
                    .build()
            }
            return INSTANCE as NotesAppRoomDatabase
        }
    }

    internal abstract fun bookMarkNotesDao(): NotesListDao
}