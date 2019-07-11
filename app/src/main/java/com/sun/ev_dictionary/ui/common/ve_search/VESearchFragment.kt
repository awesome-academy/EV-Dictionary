package com.sun.ev_dictionary.ui.common.ve_search

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseAdapter
import com.sun.ev_dictionary.base.BaseFragment
import com.sun.ev_dictionary.data.model.VietnameseWord
import com.sun.ev_dictionary.data.source.local.VietnameseWordsLocalDataSource
import com.sun.ev_dictionary.data.source.local.WordDatabase
import com.sun.ev_dictionary.data.source.repository.VietnameseWordsRepository
import com.sun.ev_dictionary.databinding.FragmentCommonSearchBinding

class VESearchFragment : BaseFragment<FragmentCommonSearchBinding>(),
    OnWordSearchClickListener, TextWatcher, OnWordSearchListener {

    private lateinit var viewModel: VESearchViewModel
    override fun getLayoutResource(): Int = R.layout.fragment_common_search

    override fun initData() {
        initViewModel()
        initWordsSearchAdapter()
        initEvent()
    }

    override fun afterTextChanged(s: Editable?) {
        //Do nothing
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //Do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.getSearchingWords(s)
    }

    override fun onWordClicked(vietnameseWord: VietnameseWord) {
        //TODO next pull
    }

    override fun onHavingNoWord() {
        Toast.makeText(
            activity,
            activity!!.getText(R.string.ve_search_msg_no_word),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initViewModel() {
        val viewModelFactory = VESearchViewModelFactory(
            VietnameseWordsRepository.getInstance(
                VietnameseWordsLocalDataSource.getInstance(
                    null,
                    WordDatabase.getInstance(activity!!).vietnameseWordDao,
                    null
                )
            ), this
        )

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(VESearchViewModel::class.java)

    }

    private fun initWordsSearchAdapter() {
        val wordsSearchAdapter = BaseAdapter<VietnameseWord>(
            activity!!, R.layout.item_search_vietnamese_word, this
        )
        binding.recyclerWords.apply {
            adapter = wordsSearchAdapter
            addItemDecoration(
                DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL)
            )
        }
        viewModel.vietnameseWords.observe(this, Observer {
            if (it != null) {
                wordsSearchAdapter.setItems(it)
            }
        })
    }

    private fun initEvent() {
        binding.textSearch.addTextChangedListener(this)
        binding.buttonBack.setOnClickListener { activity!!.finish() }
        binding.textActionBarTitle.setText(R.string.home_vietnamese_english_dictionary)
    }

    companion object {
        @JvmStatic
        fun newInstance() = VESearchFragment()
    }

}
