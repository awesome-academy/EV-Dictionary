package com.sun.ev_dictionary.ui.common.ve_search_result

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseFragment
import com.sun.ev_dictionary.data.model.VietnameseWord
import com.sun.ev_dictionary.databinding.FragmentCommonSearchResultBinding
import java.util.*

class VESearchResultFragment : BaseFragment<FragmentCommonSearchResultBinding>() {

    private lateinit var vietnameseWord: VietnameseWord
    private lateinit var viewModel: VESearchResultModel
    private lateinit var textToSpeech: TextToSpeech

    override fun getLayoutResource(): Int = R.layout.fragment_common_search_result

    override fun initData() {
        initViewModel()
        getData()
        setData()
        eventClick()
        initTextToSpeech()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!).get(VESearchResultModel::class.java)
    }

    private fun getData() {
        vietnameseWord = arguments?.getParcelable(ARGUMENT_VIETNAMESE_WORD)!!
        viewModel.setVietnameseWord(vietnameseWord)
    }

    private fun setData() {
        binding.textActionBarTitle.setText(R.string.home_vietnamese_english_dictionary)
        viewModel.vietnameseWord.observe(this, Observer {
            binding.textWord.text = it.accentedWord
            binding.textMeaning.text = it.meaning
        })
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(activity!!, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.US
            }
        })
    }
    private fun eventClick() {
        binding.buttonBack.setOnClickListener { activity!!.onBackPressed() }
        binding.buttonSpeaker.setOnClickListener { pronounceWord() }
    }

    private fun pronounceWord() {
        viewModel.pronounceWord(textToSpeech)
    }

    companion object {
        private const val ARGUMENT_VIETNAMESE_WORD = "ARGUMENT_VIETNAMESE_WORD"
        @JvmStatic
        fun newInstance(vietnameseWord: VietnameseWord) = VESearchResultFragment().apply {
            vietnameseWord.let {
                arguments = Bundle().apply {
                    putParcelable(ARGUMENT_VIETNAMESE_WORD, it)
                }
            }
        }
    }
}
