package com.sun.ev_dictionary.ui.common.ve_search_result

import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.ev_dictionary.data.model.VietnameseWord

class VESearchResultModel : ViewModel() {
    private val _vietnameseWord = MutableLiveData<VietnameseWord>()
    val vietnameseWord: LiveData<VietnameseWord>
        get() = _vietnameseWord

    fun setVietnameseWord(vietnameseWord: VietnameseWord) {
        _vietnameseWord.value = vietnameseWord
    }

    fun pronounceWord(textToSpeech: TextToSpeech) {
        textToSpeech.speak(_vietnameseWord.value?.accentedWord, TextToSpeech.QUEUE_FLUSH, null)
    }
}
