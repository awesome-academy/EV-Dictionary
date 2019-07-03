package com.sun.ev_dictionary.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.sun.ev_dictionary.data.model.EnglishWord

@Dao
interface EnglishWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(englishWord: EnglishWord)
}
