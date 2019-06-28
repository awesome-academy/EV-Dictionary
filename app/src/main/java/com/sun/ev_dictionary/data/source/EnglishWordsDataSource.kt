package com.sun.ev_dictionary.data.source

import com.sun.ev_dictionary.data.model.EnglishWord
import io.reactivex.Single

interface EnglishWordsDataSource {
    interface Local {
        fun getWordsFromTextFile(): Single<List<EnglishWord>>
    }
}
