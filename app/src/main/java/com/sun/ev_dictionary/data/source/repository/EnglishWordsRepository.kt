package com.sun.ev_dictionary.data.source.repository

import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.EnglishWordsDataSource
import io.reactivex.Single

class EnglishWordsRepository private constructor(
    private val local: EnglishWordsDataSource.Local
) : EnglishWordsDataSource.Local {

    override fun getWordsFromTextFile(): Single<List<EnglishWord>> =
        local.getWordsFromTextFile()

    companion object {
        private var INSTANCE: EnglishWordsRepository? = null

        @JvmStatic
        fun getInstance(local: EnglishWordsDataSource.Local): EnglishWordsRepository {
            return INSTANCE ?: synchronized(EnglishWordsDataSource::class.java) {
                val instance = EnglishWordsRepository(local)
                INSTANCE = instance
                instance
            }
        }
    }
}
