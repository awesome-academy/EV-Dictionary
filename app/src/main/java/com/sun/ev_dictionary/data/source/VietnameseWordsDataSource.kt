package com.sun.ev_dictionary.data.source

import io.reactivex.Observable

interface VietnameseWordsDataSource {
    interface Local {
        fun getWordsFromTextFile(): Observable<Boolean>
        fun getWordCount(): Int
        fun saveInsertedState()
        fun getInsertedState(): Boolean
    }
}
