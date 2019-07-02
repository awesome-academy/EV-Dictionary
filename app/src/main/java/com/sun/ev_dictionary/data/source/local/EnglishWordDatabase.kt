package com.sun.ev_dictionary.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.local.EnglishWordDatabase.Companion.VERSION_DATABASE
import com.sun.ev_dictionary.data.source.local.dao.EnglishWordDao

@Database(entities = [EnglishWord::class], version = VERSION_DATABASE)
abstract class EnglishWordDatabase : RoomDatabase() {
    abstract val englishWordDao: EnglishWordDao

    companion object {
        private const val ENGLISH_WORD_DB = "english_word.db"
        const val VERSION_DATABASE = 1
        @Volatile
        private var INSTANCE: EnglishWordDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EnglishWordDatabase::class.java,
                ENGLISH_WORD_DB
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
