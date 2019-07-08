package com.sun.ev_dictionary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class HomeViewModel(
    private val englishWordsRepository: EnglishWordsRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _englishWords = MutableLiveData<List<EnglishWord>>()
    val englishWords: LiveData<List<EnglishWord>>
        get() = _englishWords

    fun getSearchingWords(query: CharSequence?) {
        if (query.toString().isEmpty()) {
            _englishWords.value = null
        }else{
            compositeDisposable.add(
                englishWordsRepository.getSearchingWords(query.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ words -> onSearching(words) },
                        { throwable -> throwable.localizedMessage })
            )
        }
    }

    private fun onSearching(englishWords: List<EnglishWord>) {
        _englishWords.value = englishWords
    }
}
