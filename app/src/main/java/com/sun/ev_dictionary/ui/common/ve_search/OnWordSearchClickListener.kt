package com.sun.ev_dictionary.ui.common.ve_search

import com.sun.ev_dictionary.base.BaseAdapter
import com.sun.ev_dictionary.data.model.VietnameseWord

interface OnWordSearchClickListener : BaseAdapter.OnItemClickListener {
    fun onWordClicked(vietnameseWord: VietnameseWord)
}
