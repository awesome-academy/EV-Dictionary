package com.sun.ev_dictionary.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sun.ev_dictionary.data.model.EnglishWord.Companion.TABLE_NAME
import com.sun.ev_dictionary.utils.Constants.FAVORITE_DEFAULT
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class EnglishWord(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_WORD)
    var word: String,
    @ColumnInfo(name = COLUMN_PRONUNCIATION)
    var pronunciation: String? = null,
    @ColumnInfo(name = COLUMN_MEANING)
    var meaning: String,
    @ColumnInfo(name = COLUMN_FAVORITE)
    var favorite: Int = FAVORITE_DEFAULT
) : Parcelable {

    companion object {
        const val TABLE_NAME = "english_word"
        const val COLUMN_WORD = "word"
        const val COLUMN_PRONUNCIATION = "pronunciation"
        const val COLUMN_MEANING = "meaning"
        const val COLUMN_FAVORITE = "favorite"
    }
}
