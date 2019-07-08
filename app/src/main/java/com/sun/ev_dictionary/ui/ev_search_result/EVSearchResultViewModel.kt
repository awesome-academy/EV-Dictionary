package com.sun.ev_dictionary.ui.ev_search_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.ev_dictionary.data.model.EnglishWord

class EVSearchResultViewModel : ViewModel() {
    private val _englishWord = MutableLiveData<EnglishWord>()
    val englishWord: LiveData<EnglishWord>
        get() = _englishWord

    fun setEnglishWord(englishWord: EnglishWord){
        _englishWord.value = englishWord
    }
}
