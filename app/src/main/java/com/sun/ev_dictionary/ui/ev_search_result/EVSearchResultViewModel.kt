package com.sun.ev_dictionary.ui.ev_search_result

import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.ev_dictionary.data.model.EnglishWord

class EVSearchResultViewModel : ViewModel() {
    private val _englishWord = MutableLiveData<EnglishWord>()
    val englishWord: LiveData<EnglishWord>
        get() = _englishWord

    fun setEnglishWord(englishWord: EnglishWord) {
        _englishWord.value = englishWord
    }

    fun pronounceWord(textToSpeech: TextToSpeech) {
        textToSpeech.speak(_englishWord.value?.word, TextToSpeech.QUEUE_FLUSH, null)
    }
}
