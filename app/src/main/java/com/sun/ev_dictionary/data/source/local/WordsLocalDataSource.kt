package com.sun.ev_dictionary.data.source.local

import android.content.Context
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.WordsDataSource
import com.sun.ev_dictionary.utils.Constants.FILE_NAME_EV
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_TYPE_1
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_TYPE_2
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_TYPE_3_END
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_TYPE_3_START
import com.sun.ev_dictionary.utils.StringUtils
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.Callable

class WordsLocalDataSource : WordsDataSource.Local {
    override fun getWordsFromTextFile(context: Context): Single<List<EnglishWord>> {
        return Observable.fromCallable(Callable<List<String>> {
            getLines(context)
        })
            .flatMapIterable { lines -> lines }
            .map { line -> convertLineToEnglishWord(line) }
            .toList()
    }

    private fun getLines(context: Context): List<String> {
        val bufferedReadable = context.applicationContext.assets
            .open(FILE_NAME_EV)
            .bufferedReader()
        bufferedReadable.useLines { lines ->
            return lines.toList()
        }
    }

    private fun convertLineToEnglishWord(line: String): EnglishWord =
        when {
            line.contains(WORD_REGEX_TYPE_1) -> {
                val word: String = line.substring(0, line.indexOf(WORD_REGEX_TYPE_1))
                val meaning: String = line.substring(
                    line.indexOf(WORD_REGEX_TYPE_1) + WORD_REGEX_TYPE_1.length,
                    line.length
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }
            line.contains(WORD_REGEX_TYPE_2) -> {
                val word: String = line.substring(0, line.indexOf(WORD_REGEX_TYPE_2))
                val meaning: String = line.substring(
                    line.indexOf(WORD_REGEX_TYPE_2) + WORD_REGEX_TYPE_2.length,
                    line.length
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }
            line.contains(WORD_REGEX_TYPE_3_START) && line.contains(WORD_REGEX_TYPE_3_END) -> {
                val word: String =
                    line.substring(0, line.indexOf(WORD_REGEX_TYPE_3_START))
                val pronunciation: String = line.substring(
                    line.indexOf(WORD_REGEX_TYPE_3_START) + WORD_REGEX_TYPE_3_START.length,
                    line.indexOf(WORD_REGEX_TYPE_3_END)
                )
                val meaning: String = line.substring(
                    line.indexOf(WORD_REGEX_TYPE_3_END) + WORD_REGEX_TYPE_3_END.length,
                    line.length
                )
                EnglishWord(word, pronunciation, StringUtils.replaceChars(meaning))
            }
            else -> EnglishWord("", "", "")
        }

    companion object {
        private var sInstance: WordsLocalDataSource? = null

        @JvmStatic
        fun getInstance(): WordsLocalDataSource {
            if (sInstance == null) {
                sInstance = WordsLocalDataSource()
            }
            return sInstance!!
        }
    }
}
