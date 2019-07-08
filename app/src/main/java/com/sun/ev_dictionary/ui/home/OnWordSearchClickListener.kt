package com.sun.ev_dictionary.ui.home

import com.sun.ev_dictionary.base.BaseAdapter
import com.sun.ev_dictionary.data.model.EnglishWord

interface OnWordSearchClickListener : BaseAdapter.OnItemClickListener {
    fun onWordClicked(englishWord: EnglishWord)
}
