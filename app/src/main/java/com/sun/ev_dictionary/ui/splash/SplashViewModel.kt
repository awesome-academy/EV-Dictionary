package com.sun.ev_dictionary.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.ev_dictionary.base.BaseViewModel
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SplashViewModel(
    private val englishWordsRepository: EnglishWordsRepository
) : BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var _countProgress = 0
    private val _numberOfWordsInserted = MutableLiveData<Int>()
    val numberOfWordsInserted: LiveData<Int>
        get() = _numberOfWordsInserted
    private val _numberOfWords = MutableLiveData<Int>()
    val numberOfWords: LiveData<Int>
        get() = _numberOfWords
    private val _isWordInsertionFinished = MutableLiveData<Boolean>()
    val isWordsInsertionFinished: LiveData<Boolean>
        get() = _isWordInsertionFinished

    init {
        _isWordInsertionFinished.value = false
        _numberOfWordsInserted.value = 0
    }

    override fun onStart() {
        if (!englishWordsRepository.getInsertedState()) {
            getData()
        } else {
            _isWordInsertionFinished.value = true
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    private fun getData() {
        compositeDisposable.add(
            englishWordsRepository.getWordsFromTextFile()
                .doOnNext { isInserted -> increaseInsertedWords(isInserted) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onInsertFinish() },
                    { throwable -> throwable.localizedMessage })
        )
    }

    private fun getNumberOfWords() {
        _numberOfWords.value = englishWordsRepository.getWordCount()
    }

    private fun increaseInsertedWords(isInserted: Boolean) {
        if (isInserted) {
            _countProgress++
            _numberOfWordsInserted.postValue(_countProgress)
        }
    }

    private fun onInsertFinish() {
        getNumberOfWords()
        if (numberOfWordsInserted.value == englishWordsRepository.getWordCount()) {
            _isWordInsertionFinished.value = true
            englishWordsRepository.saveInsertedState()
        }
    }
}
