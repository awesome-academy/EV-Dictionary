package com.sun.ev_dictionary.data.source.local

import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.EnglishWordsDataSource
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_TYPE_1
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_TYPE_2
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_TYPE_3_END
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_TYPE_3_START
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_TYPE_4_END
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_TYPE_5_END
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_TYPE_5_START
import com.sun.ev_dictionary.utils.StringUtils
import io.reactivex.Observable
import io.reactivex.Single
import java.io.BufferedReader
import java.util.concurrent.Callable

class EnglishWordsLocalDataSource private constructor(
    private val bufferedReader: BufferedReader
) : EnglishWordsDataSource.Local {

    override fun getWordsFromTextFile(): Single<List<EnglishWord>> {
        return Observable.fromCallable(Callable<List<String>> {
            getLines(bufferedReader)
        })
            .flatMapIterable { lines -> lines }
            .map { line -> convertLineToEnglishWord(line) }
            .toList()
    }

    private fun getLines(bufferedReader: BufferedReader): List<String> {
        bufferedReader.useLines { lines ->
            return lines.toList()
        }
    }

    private fun convertLineToEnglishWord(line: String): EnglishWord =
        when {
            line.contains(EN_WORD_REGEX_TYPE_1) -> {
                val word: String = line.substring(0, line.indexOf(EN_WORD_REGEX_TYPE_1))
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_TYPE_1) + EN_WORD_REGEX_TYPE_1.length,
                    line.length
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }
            line.contains(EN_WORD_REGEX_TYPE_2) -> {
                val word: String = line.substring(0, line.indexOf(EN_WORD_REGEX_TYPE_2))
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_TYPE_2) + EN_WORD_REGEX_TYPE_2.length,
                    line.length
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }
            line.contains(EN_WORD_REGEX_TYPE_3_START) && line.contains(EN_WORD_REGEX_TYPE_3_END) -> {
                val word: String =
                    line.substring(0, line.indexOf(EN_WORD_REGEX_TYPE_3_START))
                val pronunciation: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_TYPE_3_START) + EN_WORD_REGEX_TYPE_3_START.length,
                    line.indexOf(EN_WORD_REGEX_TYPE_3_END)
                )
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_TYPE_3_END) + EN_WORD_REGEX_TYPE_3_END.length,
                    line.length
                )
                EnglishWord(word, pronunciation, StringUtils.replaceChars(meaning))
            }
            line.contains(EN_WORD_REGEX_TYPE_3_START) && line.contains(EN_WORD_REGEX_TYPE_4_END) -> {
                val word: String =
                    line.substring(0, line.indexOf(EN_WORD_REGEX_TYPE_3_START))
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_TYPE_3_START) + EN_WORD_REGEX_TYPE_3_START.length,
                    line.indexOf(EN_WORD_REGEX_TYPE_4_END)
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }
            line.contains(EN_WORD_REGEX_TYPE_5_END) -> {
                val word: String =
                    line.substring(0, line.indexOf(EN_WORD_REGEX_TYPE_5_END))
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_TYPE_5_END) + EN_WORD_REGEX_TYPE_5_END.length,
                    line.length
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }
            else -> {
                val word: String =
                    line.substring(0, line.indexOf(EN_WORD_REGEX_TYPE_5_START))
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_TYPE_5_START) + EN_WORD_REGEX_TYPE_5_START.length,
                    line.indexOf(EN_WORD_REGEX_TYPE_5_END)
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }

        }

    companion object {
        private var INSTANCE: EnglishWordsLocalDataSource? = null

        @JvmStatic
        fun getInstance(bufferedReader: BufferedReader): EnglishWordsLocalDataSource {
            return INSTANCE ?: synchronized(EnglishWordsLocalDataSource::class.java) {
                val instance = EnglishWordsLocalDataSource(bufferedReader)
                INSTANCE = instance
                instance
            }
        }
    }
}
