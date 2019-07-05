package com.sun.ev_dictionary.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sun.ev_dictionary.data.model.VietnameseWord.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class VietnameseWord(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ACCENTED_WORD)
    var accentedWord: String,
    @ColumnInfo(name = COLUMN_NON_ACCENTED_WORD)
    var nonAccentedWord: String,
    @ColumnInfo(name = COLUMN_MEANING)
    var meaning: String
) {

    companion object {
        const val TABLE_NAME = "vietnamese_word"
        const val COLUMN_ACCENTED_WORD = "accented_word"
        const val COLUMN_NON_ACCENTED_WORD = "non_accented_word"
        const val COLUMN_MEANING = "meaning"
    }
}
