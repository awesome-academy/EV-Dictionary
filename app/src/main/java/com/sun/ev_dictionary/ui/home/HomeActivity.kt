package com.sun.ev_dictionary.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseActivity
import com.sun.ev_dictionary.base.BaseAdapter
import com.sun.ev_dictionary.data.model.EnglishWord
import com.sun.ev_dictionary.data.source.local.EnglishWordsLocalDataSource
import com.sun.ev_dictionary.data.source.local.WordDatabase
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository
import com.sun.ev_dictionary.databinding.ActivityHomeBinding
import com.sun.ev_dictionary.ui.common.CommonActivity
import com.sun.ev_dictionary.ui.common.ve_search.VESearchFragment
import com.sun.ev_dictionary.ui.ev_search_result.EVSearchResultActivity
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : BaseActivity<ActivityHomeBinding>(), OnWordSearchClickListener {

    private lateinit var viewModel: HomeViewModel

    override fun getLayoutResource(): Int = R.layout.activity_home

    override fun initData() {
        initViewModel()
        setWordsSearchAdapter()
        setEventClick()
    }

    override fun onWordClicked(englishWord: EnglishWord) {
        EVSearchResultActivity.getIntent(this, englishWord).apply {
            startActivity(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val textResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    setTextSearch(textResult[0])
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        textSearch.text.clear()
        recyclerWords.visibility = View.GONE
    }

    private fun initViewModel() {
        val viewModelFactory = HomeViewModelFactory(
            EnglishWordsRepository.getInstance(
                EnglishWordsLocalDataSource.getInstance(
                    null,
                    WordDatabase.getInstance(this).englishWordDao,
                    null
                )
            )
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun setEventClick() {
        buttonMicrophone.setOnClickListener { speechToText() }
        textVED.setOnClickListener {
            navigateToOtherScreen(
                CommonActivity.getIntent(this, VESearchFragment::class.java.simpleName)
            )
        }
    }

    private fun setWordsSearchAdapter() {
        val wordSearchAdapter = BaseAdapter<EnglishWord>(
            this, R.layout.item_search_english_word, this
        )
        recyclerWords.apply {
            adapter = wordSearchAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        viewModel.englishWords.observe(this, Observer {
            if (it == null) {
                recyclerWords.visibility = View.GONE
            } else if (it.isNotEmpty()) {
                recyclerWords.visibility = View.VISIBLE
                wordSearchAdapter.setItems(it)
            }
        })
    }

    private fun setTextSearch(text: String) {
        textSearch.setText(text)
    }

    private fun speechToText() {
        try {
            startActivityForResult(getIntentSpeech(this), REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun navigateToOtherScreen(intent: Intent) {
        startActivity(intent)
    }

    companion object {
        private const val REQUEST_CODE_SPEECH_INPUT: Int = 123

        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java)

        fun getIntentSpeech(context: Context): Intent {
            return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    context.getString(R.string.home_text_promt)
                )
            }
        }
    }
}
