package com.sun.ev_dictionary.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    abstract fun onStart()

    abstract fun onStop()
}
