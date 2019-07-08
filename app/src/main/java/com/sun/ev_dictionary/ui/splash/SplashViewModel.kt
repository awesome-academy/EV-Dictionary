package com.sun.ev_dictionary.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sun.ev_dictionary.base.BaseViewModel
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository
import com.sun.ev_dictionary.data.source.repository.VietnameseWordsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SplashViewModel(
    private val englishWordsRepository: EnglishWordsRepository,
    private val vietnameseWordsRepository: VietnameseWordsRepository
) : BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var _countEnglishWords = 0
    private var _countVietnameseWords = 0

    private val _numberOfEnglishWordsInserted = MutableLiveData<Int>()
    val numberOfEnglishWordsInserted: LiveData<Int>
        get() = _numberOfEnglishWordsInserted

    private val _numberOfEnglishWords = MutableLiveData<Int>()
    val numberOfEnglishWords: LiveData<Int>
        get() = _numberOfEnglishWords

    private val _numberOfVietnameseWordsInserted = MutableLiveData<Int>()
    val numberOfVietnameseWordsInserted: LiveData<Int>
        get() = _numberOfVietnameseWordsInserted

    private val _numberOfVietnameseWords = MutableLiveData<Int>()
    val numberOfVietnameseWords: LiveData<Int>
        get() = _numberOfVietnameseWords

    private val _isWordsInsertionFinished = MutableLiveData<Boolean>()
    val isWordsInsertionFinished: LiveData<Boolean>
        get() = _isWordsInsertionFinished

    init {
        _isWordsInsertionFinished.value = false
        _numberOfEnglishWordsInserted.value = 0
        _numberOfVietnameseWordsInserted.value = 0
    }

    override fun onStart() {
        if (!englishWordsRepository.getInsertedState()!!
            && !vietnameseWordsRepository.getInsertedState()!!
        ) {
            getEnglishData()
            getVietnameseData()
        } else {
            _isWordsInsertionFinished.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun getEnglishData() {
        compositeDisposable.add(
            englishWordsRepository.getWordsFromTextFile()
                .doOnNext { isInserted -> increaseEnglishWordsInserted(isInserted) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onInsertEnglishWordFinish() },
                    { throwable -> throwable.localizedMessage })
        )
    }

    private fun getVietnameseData() {
        compositeDisposable.add(
            vietnameseWordsRepository.getWordsFromTextFile()
                .doOnNext { isInserted -> increaseVietnameseWordsInserted(isInserted) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onInsertVietnameseWordFinish() },
                    { throwable -> throwable.localizedMessage })
        )
    }

    private fun getNumberOfEnglishWords() {
        _numberOfEnglishWords.value = englishWordsRepository.getWordCount()
    }

    private fun getNumberOfVietnameseWords() {
        _numberOfVietnameseWords.value = vietnameseWordsRepository.getWordCount()
    }

    private fun increaseEnglishWordsInserted(isInserted: Boolean) {
        if (isInserted) {
            _countEnglishWords++
            _numberOfEnglishWordsInserted.postValue(_countEnglishWords)
        }
    }

    private fun increaseVietnameseWordsInserted(isInserted: Boolean) {
        if (isInserted) {
            _countVietnameseWords++
            _numberOfVietnameseWordsInserted.postValue(_countVietnameseWords)
        }
    }

    private fun onInsertEnglishWordFinish() {
        if (numberOfEnglishWordsInserted.value == englishWordsRepository.getWordCount()) {
            englishWordsRepository.saveInsertedState()
        }
        getNumberOfEnglishWords()
        onInsertFinish()
    }

    private fun onInsertVietnameseWordFinish() {
        if (numberOfVietnameseWordsInserted.value == vietnameseWordsRepository.getWordCount()) {
            vietnameseWordsRepository.saveInsertedState()
        }
        getNumberOfVietnameseWords()
        onInsertFinish()
    }

    private fun onInsertFinish() {
        if (numberOfVietnameseWordsInserted.value == vietnameseWordsRepository.getWordCount()
            && numberOfEnglishWordsInserted.value == englishWordsRepository.getWordCount()
        ) {
            _isWordsInsertionFinished.value = true
        }
    }
}
