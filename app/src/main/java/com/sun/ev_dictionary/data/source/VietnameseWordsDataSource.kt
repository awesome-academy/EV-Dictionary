package com.sun.ev_dictionary.data.source

import com.sun.ev_dictionary.data.model.VietnameseWord
import io.reactivex.Observable
import io.reactivex.Single

interface VietnameseWordsDataSource {
    interface Local {
        fun getWordsFromTextFile(): Observable<Boolean>
        fun getWordCount(): Int
        fun saveInsertedState(): Unit?
        fun getInsertedState(): Boolean?
        fun getSearchingWords(query: String?): Single<List<VietnameseWord>>
    }
}
