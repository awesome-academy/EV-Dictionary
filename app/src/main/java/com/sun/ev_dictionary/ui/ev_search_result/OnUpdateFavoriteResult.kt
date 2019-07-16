package com.sun.ev_dictionary.ui.ev_search_result

interface OnUpdateFavoriteResult {
    fun onComplete(isSuccessful: Boolean)
    fun onError(t: Throwable)
}
