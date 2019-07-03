package com.sun.ev_dictionary.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.local.WordDatabase.Companion.VERSION_DATABASE
import com.sun.ev_dictionary.data.source.local.dao.EnglishWordDao

@Database(entities = [EnglishWord::class], version = VERSION_DATABASE)
abstract class WordDatabase : RoomDatabase() {
    abstract val englishWordDao: EnglishWordDao

    companion object {
        private const val WORD_DB = "word.db"
        const val VERSION_DATABASE = 1
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WordDatabase::class.java,
                WORD_DB
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
