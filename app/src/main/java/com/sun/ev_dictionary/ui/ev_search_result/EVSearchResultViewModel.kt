package com.sun.ev_dictionary.ui.ev_search_result

import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository
import com.sun.ev_dictionary.ui.home.HomeViewModel
import com.sun.ev_dictionary.utils.Constants.FAVORITE_DEFAULT
import com.sun.ev_dictionary.utils.Constants.FAVORITE_SELECTED
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EVSearchResultViewModel(
    private val englishWordsRepository: EnglishWordsRepository,
    private val onUpdateFavoriteResult: OnUpdateFavoriteResult
) : HomeViewModel(englishWordsRepository), OnUpdateFavoriteResult {

    private val _englishWord = MutableLiveData<EnglishWord>()
    val englishWord: LiveData<EnglishWord>
        get() = _englishWord

    fun setEnglishWord(englishWord: EnglishWord) {
        _englishWord.value = englishWord
    }

    fun pronounceWord(textToSpeech: TextToSpeech) {
        textToSpeech.speak(_englishWord.value?.word, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun updateFavorite() {
        val tempEnglishWord = _englishWord.value?.copy()
        when (tempEnglishWord?.favorite) {
            FAVORITE_DEFAULT -> tempEnglishWord.favorite = FAVORITE_SELECTED
            FAVORITE_SELECTED -> tempEnglishWord.favorite = FAVORITE_DEFAULT
        }
        _englishWord.value = tempEnglishWord

        _englishWord.value?.favorite?.let { status ->
            _englishWord.value?.word?.let { word ->
                englishWordsRepository.updateFavorite(word, status)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::onComplete, ::onError)
            }
        }
    }

    override fun onComplete(isSuccessful: Boolean) {
        onUpdateFavoriteResult.onComplete(isSuccessful)
    }

    override fun onError(t: Throwable) {
        onUpdateFavoriteResult.onError(t)
    }
}
