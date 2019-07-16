package com.sun.ev_dictionary.ui.ev_search_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository

@Suppress("UNCHECKED_CAST")
class EVSearchResultViewModelFactory(
    private val englishWordsRepository: EnglishWordsRepository,
    private val onUpdateFavoriteResult: OnUpdateFavoriteResult
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EVSearchResultViewModel::class.java)) {
            return EVSearchResultViewModel(
                englishWordsRepository,
                onUpdateFavoriteResult
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
