package com.sun.ev_dictionary.ui.common.ve_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sun.ev_dictionary.data.model.VietnameseWord
import com.sun.ev_dictionary.data.source.repository.VietnameseWordsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VESearchViewModel(
    private val vietnameseWordsRepository: VietnameseWordsRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _vietnameseWords = MutableLiveData<List<VietnameseWord>>()
    val vietnameseWords: LiveData<List<VietnameseWord>>
        get() = _vietnameseWords

    fun getSearchingWords(query: CharSequence?) {
        if (query.toString().isEmpty()) {
            _vietnameseWords.value = null
        } else {
            compositeDisposable.add(
                vietnameseWordsRepository.getSearchingWords(query.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ words -> onSearching(words) },
                        { throwable -> throwable.localizedMessage })
            )
        }
    }

    private fun onSearching(vietnameseWords: List<VietnameseWord>) {
        _vietnameseWords.value = vietnameseWords
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
