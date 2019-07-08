package com.sun.ev_dictionary.ui.ev_search_result

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseActivity
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.databinding.ActivityEvSearchResultBinding

class EVSearchResultActivity : BaseActivity<ActivityEvSearchResultBinding>() {

    private lateinit var englishWord: EnglishWord
    private lateinit var viewModel: EVSearchResultViewModel
    override fun getLayoutResource(): Int = R.layout.activity_ev_search_result

    override fun initData() {
        englishWord = intent?.getParcelableExtra(EXTRA_ENGLISH_WORD)!!
        setViewModel(englishWord)
    }

    private fun setViewModel(englishWord: EnglishWord) {
        viewModel = ViewModelProviders.of(this).get(EVSearchResultViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.setEnglishWord(englishWord)
    }

    companion object {
        const val EXTRA_ENGLISH_WORD =
            "com.sun.ev_dictionary.ui.ev_search_result.EXTRA_ENGLISH_WORD"

        fun getIntent(context: Context, englishWord: EnglishWord) =
            Intent(context, EVSearchResultActivity::class.java).apply {
                putExtra(EXTRA_ENGLISH_WORD, englishWord)
            }
    }
}
