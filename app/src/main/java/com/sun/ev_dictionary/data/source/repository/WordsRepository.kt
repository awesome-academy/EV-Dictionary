package com.sun.ev_dictionary.data.source.repository

import android.content.Context
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.WordsDataSource
import io.reactivex.Single

class WordsRepository private constructor(
    private val local: WordsDataSource.Local
) : WordsDataSource.Local {

    override fun getWordsFromTextFile(context: Context): Single<List<EnglishWord>> =
        local.getWordsFromTextFile(context)

    companion object {
        private var sInstance: WordsRepository? = null

        @JvmStatic
        fun getInstance(local: WordsDataSource.Local): WordsRepository {
            if (sInstance == null) {
                sInstance = WordsRepository(local)
            }
            return sInstance!!
        }
    }
}
