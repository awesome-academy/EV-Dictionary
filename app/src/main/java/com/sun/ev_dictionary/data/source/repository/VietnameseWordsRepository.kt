package com.sun.ev_dictionary.data.source.repository

import com.sun.ev_dictionary.data.source.VietnameseWordsDataSource
import io.reactivex.Observable

class VietnameseWordsRepository private constructor(
    private val local: VietnameseWordsDataSource.Local
) : VietnameseWordsDataSource.Local {

    override fun getWordCount(): Int = local.getWordCount()
    override fun getWordsFromTextFile(): Observable<Boolean> =
        local.getWordsFromTextFile()

    override fun getInsertedState(): Boolean = local.getInsertedState()
    override fun saveInsertedState() = local.saveInsertedState()

    companion object {
        private var INSTANCE: VietnameseWordsRepository? = null

        @JvmStatic
        fun getInstance(local: VietnameseWordsDataSource.Local): VietnameseWordsRepository {
            return INSTANCE ?: synchronized(VietnameseWordsDataSource::class.java) {
                val instance = VietnameseWordsRepository(local)
                INSTANCE = instance
                instance
            }
        }
    }
}
