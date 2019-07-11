package com.sun.ev_dictionary.ui.common.ve_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sun.ev_dictionary.data.source.repository.VietnameseWordsRepository

@Suppress("UNCHECKED_CAST")
class VESearchViewModelFactory(
    private val vietnameseWordsRepository: VietnameseWordsRepository,
    private val wordSearchListener: OnWordSearchListener
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VESearchViewModel::class.java)) {
            return VESearchViewModel(vietnameseWordsRepository, wordSearchListener) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
