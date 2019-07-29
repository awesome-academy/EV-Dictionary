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
    private val englishWordsRepository: EnglishWordsRepository
) : HomeViewModel(englishWordsRepository) {

    private val _englishWord = MutableLiveData<EnglishWord>()
    val englishWord: LiveData<EnglishWord>
        get() = _englishWord

    private val _isAddToFav = MutableLiveData<Boolean>()
    val isAddToFav: LiveData<Boolean>
        get() = _isAddToFav

    private val _throwable = MutableLiveData<Throwable>()
    val throwable: LiveData<Throwable>
        get() = _throwable

    init {
        _isAddToFav.value = false
    }

    fun setEnglishWord(englishWord: EnglishWord) {
        _englishWord.value = englishWord
    }

    fun pronounceWord(textToSpeech: TextToSpeech) {
        textToSpeech.speak(_englishWord.value?.word, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun updateFavorite() {
        val tempEnglishWord = _englishWord.value?.copy()
        tempEnglishWord?.favorite = when (tempEnglishWord?.favorite) {
            FAVORITE_DEFAULT -> FAVORITE_SELECTED
            else -> FAVORITE_DEFAULT
        }
        _englishWord.value = tempEnglishWord?.apply {
            englishWordsRepository.updateFavorite(word, favorite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onCompleted,::onError)
        }
    }

    private fun onCompleted(){
        _isAddToFav.value = true
    }

    private fun onError(t : Throwable){
        _throwable.value = t
    }
}
