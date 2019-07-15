package com.sun.ev_dictionary.data.source.local

import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.EnglishWordsDataSource
import com.sun.ev_dictionary.data.source.local.dao.EnglishWordDao
import com.sun.ev_dictionary.utils.Constants
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_END_TYPE_1
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_END_TYPE_2
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_START
import com.sun.ev_dictionary.utils.SharedPreference
import com.sun.ev_dictionary.utils.StringUtils
import io.reactivex.Observable
import io.reactivex.Single
import java.io.BufferedReader

class EnglishWordsLocalDataSource private constructor(
    private val bufferedReader: BufferedReader?,
    private val englishWordDao: EnglishWordDao,
    private val sharedPreference: SharedPreference?
) : EnglishWordsDataSource.Local {

    private var _numberOfWords = 0

    override fun getWordCount(): Int = _numberOfWords

    override fun getWordsFromTextFile(): Observable<Boolean> {
        return Observable.fromCallable {
            bufferedReader?.let { getLines(it) }
        }
            .flatMapIterable { lines -> lines }
            .map { line -> convertLineToEnglishWord(line) }
            .map { englishWord -> insertWordsToDatabase(englishWord) }
    }

    override fun getSearchingWords(query: String?): Single<List<EnglishWord>> =
        englishWordDao.queryWord(query)

    override fun saveInsertedState() =
        sharedPreference?.save(Constants.PREF_ENGLISH_WORDS, true)

    override fun getInsertedState(): Boolean? =
        sharedPreference?.getValueBoolean(Constants.PREF_ENGLISH_WORDS, false)

    private fun getLines(bufferedReader: BufferedReader): List<String> {
        bufferedReader.useLines { lines ->
            return lines.toList().also { _numberOfWords = it.size }
        }
    }

    private fun convertLineToEnglishWord(line: String): EnglishWord =
        when {
            line.contains(EN_WORD_REGEX_START) && line.contains(EN_WORD_REGEX_END_TYPE_1) -> {
                val word: String =
                    line.substring(0, line.indexOf(EN_WORD_REGEX_START))
                val pronunciation: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_START) + EN_WORD_REGEX_START.length,
                    line.indexOf(EN_WORD_REGEX_END_TYPE_1)
                )
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_END_TYPE_1) + EN_WORD_REGEX_END_TYPE_1.length,
                    line.length
                )
                EnglishWord(word, pronunciation, StringUtils.replaceChars(meaning))
            }
            else -> {
                val word: String =
                    line.substring(0, line.indexOf(EN_WORD_REGEX_START))
                val meaning: String = line.substring(
                    line.indexOf(EN_WORD_REGEX_END_TYPE_2) + EN_WORD_REGEX_END_TYPE_2.length,
                    line.length
                )
                EnglishWord(word, null, StringUtils.replaceChars(meaning))
            }
        }

    private fun insertWordsToDatabase(englishWord: EnglishWord): Boolean {
        englishWord.let {
            englishWordDao.insertWord(it)
            return true
        }
    }

    companion object {
        private var INSTANCE: EnglishWordsLocalDataSource? = null

        @JvmStatic
        fun getInstance(
            bufferedReader: BufferedReader?,
            englishWordDao: EnglishWordDao,
            sharedPreference: SharedPreference?
        ): EnglishWordsLocalDataSource {
            return INSTANCE ?: synchronized(EnglishWordsLocalDataSource::class.java) {
                val instance = EnglishWordsLocalDataSource(
                    bufferedReader,
                    englishWordDao,
                    sharedPreference
                )
                INSTANCE = instance
                instance
            }
        }
    }
}
