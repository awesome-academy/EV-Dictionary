package com.sun.ev_dictionary.data.source

import android.content.Context
import com.sun.ev_dictionary.data.model.EnglishWord
import io.reactivex.Single

interface WordsDataSource {
    interface Local {
        fun getWordsFromTextFile(context: Context) : Single<List<EnglishWord>>
    }
}
