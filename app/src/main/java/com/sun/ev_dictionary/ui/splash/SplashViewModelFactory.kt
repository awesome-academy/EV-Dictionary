package com.sun.ev_dictionary.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository

@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory(
    private val englishWordsRepository: EnglishWordsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(englishWordsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
