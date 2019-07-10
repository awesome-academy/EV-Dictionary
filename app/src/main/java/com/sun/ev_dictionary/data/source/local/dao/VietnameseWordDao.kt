package com.sun.ev_dictionary.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sun.ev_dictionary.data.model.VietnameseWord
import com.sun.ev_dictionary.data.model.VietnameseWord.Companion.COLUMN_ACCENTED_WORD
import com.sun.ev_dictionary.data.model.VietnameseWord.Companion.COLUMN_NON_ACCENTED_WORD
import com.sun.ev_dictionary.data.model.VietnameseWord.Companion.TABLE_NAME
import io.reactivex.Single

@Dao
interface VietnameseWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(vietnameseWord: VietnameseWord)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ACCENTED_WORD OR $COLUMN_NON_ACCENTED_WORD LIKE '%' || :query || '%'")
    fun queryWord(query: String?): Single<List<VietnameseWord>>
}
