package com.sun.ev_dictionary.ui.ev_search_result

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import com.sun.ev_dictionary.databinding.ActivityEvSearchResultBinding
import com.sun.ev_dictionary.ui.home.OnWordSearchClickListener
import kotlinx.android.synthetic.main.activity_ev_search_result.*
import java.util.*

class EVSearchResultActivity : BaseActivity<ActivityEvSearchResultBinding>(),
    TextToSpeech.OnInitListener,
    SearchView.OnQueryTextListener,
    OnWordSearchClickListener,
    OnUpdateFavoriteResult {

    private lateinit var englishWord: EnglishWord
    private lateinit var viewModel: EVSearchResultViewModel
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var searchItem: MenuItem
    override fun getLayoutResource(): Int = R.layout.activity_ev_search_result

    override fun initData() {
        englishWord = intent?.getParcelableExtra(EXTRA_ENGLISH_WORD)!!
        setViewModel(englishWord)
        textToSpeech = TextToSpeech(this, this)
        setEventClick()
        setActionBar()
        setWordsSearchAdapter()
    }

    override fun onInit(status: Int) {
        if (status != TextToSpeech.ERROR) {
            textToSpeech.language = Locale.US
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ev_search_result, menu)
        searchItem = menu?.findItem(R.id.actionSearch) ?: return false
        val searchView: SearchView = searchItem.actionView as SearchView
        val searchEditText: EditText =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text)

        searchEditText.apply {
            setTextColor(Color.WHITE)
            setHintTextColor(Color.DKGRAY)
        }
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.englishWords.observe(this, Observer {
            if (it != null) viewModel.setEnglishWord(it[0])
        })
        searchItem.collapseActionView()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.getSearchingWords(newText)
        return true
    }

    override fun onWordClicked(englishWord: EnglishWord) {
        viewModel.setEnglishWord(englishWord)
        searchItem.collapseActionView()
        recyclerWords.visibility = View.GONE
    }

    override fun onComplete(isSuccessful: Boolean) {
        if (isSuccessful){
            Toast.makeText(this,getString(R.string.ev_search_notify_success),Toast.LENGTH_SHORT).show()
        }else  Toast.makeText(this,getString(R.string.ev_search_notify_fail),Toast.LENGTH_SHORT).show()
    }

    override fun onError(t: Throwable) {
        Toast.makeText(this,getString(R.string.ev_search_notify_error),Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        recyclerWords.visibility = View.GONE
    }

    private fun setViewModel(englishWord: EnglishWord) {
        val viewModelFactory = EVSearchResultViewModelFactory(
            EnglishWordsRepository.getInstance(
                EnglishWordsLocalDataSource.getInstance(
                    null,
                    WordDatabase.getInstance(this).englishWordDao,
                    null
                )
            ),this
        )

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(EVSearchResultViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.setEnglishWord(englishWord)
    }

    private fun setWordsSearchAdapter() {
        val wordSearchAdapter = BaseAdapter<EnglishWord>(
            this, R.layout.item_search_english_word, this
        )
        recyclerWords.adapter = wordSearchAdapter
        recyclerWords.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.englishWords.observe(this, Observer {
            if (it == null) {
                recyclerWords.visibility = View.GONE
            } else if (it.isNotEmpty()) {
                recyclerWords.visibility = View.VISIBLE
                wordSearchAdapter.setItems(it)
            }
        })
    }

    private fun setEventClick() {
        buttonSpeaker.setOnClickListener { pronounceWord() }
    }

    private fun pronounceWord() {
        viewModel.pronounceWord(textToSpeech)
    }

    private fun setActionBar() {
        setSupportActionBar(toolbarEVResult)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarEVResult.setNavigationIcon(R.drawable.ic_left_arrow_white)
        toolbarEVResult.setNavigationOnClickListener {
            onBackPressed()
        }

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
