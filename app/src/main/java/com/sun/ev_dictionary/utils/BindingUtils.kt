package com.sun.ev_dictionary.utils

import android.annotation.SuppressLint
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sun.ev_dictionary.utils.Constants.FAVORITE_DEFAULT

@SuppressLint("SetTextI18n")
@BindingAdapter("bindingMaxValue", "bindingProgressValue")
fun showPercent(textView: TextView, total: Int, progress: Int) {
    if (total != 0) {
        val percent = (progress * 100) / total
        textView.text = "$percent %"
    }
}

@BindingAdapter("bindingFavoriteStatus")
fun showFavoriteStatus(favButton: ImageButton, status: Int) {
    favButton.isSelected = status != FAVORITE_DEFAULT
}
