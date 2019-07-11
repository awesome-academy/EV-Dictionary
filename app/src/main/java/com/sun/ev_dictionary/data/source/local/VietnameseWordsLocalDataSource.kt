package com.sun.ev_dictionary.data.source.local

import com.sun.ev_dictionary.data.model.VietnameseWord
import com.sun.ev_dictionary.data.source.VietnameseWordsDataSource
import com.sun.ev_dictionary.data.source.local.dao.VietnameseWordDao
import com.sun.ev_dictionary.utils.Constants
import com.sun.ev_dictionary.utils.Constants.VI_WORD_REGEX_START
import com.sun.ev_dictionary.utils.Constants.VI_WORD_REGEX_TYPE_1_END
import com.sun.ev_dictionary.utils.Constants.VI_WORD_REGEX_TYPE_2_END
import com.sun.ev_dictionary.utils.Constants.VI_WORD_REGEX_TYPE_3_END
import com.sun.ev_dictionary.utils.SharedPreference
import com.sun.ev_dictionary.utils.StringUtils
import io.reactivex.Observable
import io.reactivex.Single
import java.io.BufferedReader

class VietnameseWordsLocalDataSource private constructor(
    private val bufferedReader: BufferedReader?,
    private val vietnameseWordDao: VietnameseWordDao,
    private val sharedPreference: SharedPreference?
) : VietnameseWordsDataSource.Local {

    private var _numberOfWords = 0

    override fun getWordsFromTextFile(): Observable<Boolean> {
        return Observable.fromCallable {
            bufferedReader?.let { getLines(it) }
        }
            .flatMapIterable { lines -> lines }
            .map { line -> convertLineToVietnameseWord(line) }
            .map { vietnameseWord -> insertWordsToDatabase(vietnameseWord) }
    }

    override fun getWordCount(): Int = _numberOfWords

    override fun saveInsertedState() =
        sharedPreference?.save(Constants.PREF_VIETNAMESE_WORDS, true)

    override fun getInsertedState(): Boolean? =
        sharedPreference?.getValueBoolean(Constants.PREF_VIETNAMESE_WORDS, false)

    override fun getSearchingWords(query: String?): Single<List<VietnameseWord>> =
        vietnameseWordDao.queryWord(query)

    private fun getLines(bufferedReader: BufferedReader): List<String> {
        bufferedReader.useLines { lines ->
            return lines.toList().also { _numberOfWords = it.size }
        }
    }

    private fun convertLineToVietnameseWord(line: String): VietnameseWord? =
        when {
            line.contains(VI_WORD_REGEX_TYPE_1_END) -> {
                val accentedWord: String = line.substring(0, line.indexOf(VI_WORD_REGEX_START))
                val nonAccentedWord: String = line.substring(
                    line.indexOf(VI_WORD_REGEX_START) + VI_WORD_REGEX_START.length,
                    line.indexOf(VI_WORD_REGEX_TYPE_1_END)
                )
                val meaning: String = line.substring(
                    line.indexOf(VI_WORD_REGEX_TYPE_1_END) + VI_WORD_REGEX_TYPE_1_END.length,
                    line.length
                )
                VietnameseWord(accentedWord, nonAccentedWord, StringUtils.replaceChars(meaning))

            }
            line.contains(VI_WORD_REGEX_TYPE_2_END) -> {
                val accentedWord: String = line.substring(0, line.indexOf(VI_WORD_REGEX_START))
                val nonAccentedWord: String = line.substring(
                    line.indexOf(VI_WORD_REGEX_START) + VI_WORD_REGEX_START.length,
                    line.indexOf(VI_WORD_REGEX_TYPE_2_END)
                )
                val meaning: String = line.substring(
                    line.indexOf(VI_WORD_REGEX_TYPE_2_END) + VI_WORD_REGEX_TYPE_2_END.length,
                    line.length
                )
                VietnameseWord(accentedWord, nonAccentedWord, StringUtils.replaceChars(meaning))

            }
            else -> {
                val accentedWord: String = line.substring(0, line.indexOf(VI_WORD_REGEX_START))
                val nonAccentedWord: String = line.substring(
                    line.indexOf(VI_WORD_REGEX_START) + VI_WORD_REGEX_START.length,
                    line.indexOf(VI_WORD_REGEX_TYPE_3_END)
                )
                val meaning: String = line.substring(
                    line.indexOf(VI_WORD_REGEX_TYPE_3_END) + VI_WORD_REGEX_TYPE_3_END.length,
                    line.length
                )
                VietnameseWord(accentedWord, nonAccentedWord, StringUtils.replaceChars(meaning))
            }
        }

    private fun insertWordsToDatabase(vietnameseWord: VietnameseWord): Boolean {
        vietnameseWord.let {
            vietnameseWordDao.insertWord(it)
            return true
        }
    }

    companion object {
        private var INSTANCE: VietnameseWordsLocalDataSource? = null

        @JvmStatic
        fun getInstance(
            bufferedReader: BufferedReader?,
            vietnameseWordDao: VietnameseWordDao,
            sharedPreference: SharedPreference?
        ): VietnameseWordsLocalDataSource {
            return INSTANCE ?: synchronized(VietnameseWordsLocalDataSource::class.java) {
                val instance = VietnameseWordsLocalDataSource(
                    bufferedReader,
                    vietnameseWordDao,
                    sharedPreference
                )
                INSTANCE = instance
                instance
            }
        }
    }
}
