package com.sun.ev_dictionary.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.model.EnglishWord.Companion.COLUMN_WORD
import com.sun.ev_dictionary.data.model.EnglishWord.Companion.TABLE_NAME
import io.reactivex.Single

@Dao
interface EnglishWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(englishWord: EnglishWord)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_WORD LIKE '%' || :query || '%'")
    fun queryWord(query: String?): Single<List<EnglishWord>>

}
