package com.sun.ev_dictionary.data.source.repository

import com.sun.ev_dictionary.data.source.EnglishWordsDataSource
import io.reactivex.Observable

class EnglishWordsRepository private constructor(
    private val local: EnglishWordsDataSource.Local
) : EnglishWordsDataSource.Local {

    override fun getWordCount(): Int = local.getWordCount()
    override fun getWordsFromTextFile(): Observable<Boolean> =
        local.getWordsFromTextFile()

    override fun getInsertedState(): Boolean = local.getInsertedState()
    override fun saveInsertedState() = local.saveInsertedState()

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
