package com.sun.ev_dictionary.ui.splash

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseActivity
import com.sun.ev_dictionary.data.source.local.EnglishWordsLocalDataSource
import com.sun.ev_dictionary.data.source.local.VietnameseWordsLocalDataSource
import com.sun.ev_dictionary.data.source.local.WordDatabase
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository
import com.sun.ev_dictionary.data.source.repository.VietnameseWordsRepository
import com.sun.ev_dictionary.databinding.ActivitySplashBinding
import com.sun.ev_dictionary.ui.home.HomeActivity
import com.sun.ev_dictionary.utils.Constants
import com.sun.ev_dictionary.utils.SharedPreference

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private lateinit var viewModel: SplashViewModel

    override fun getLayoutResource(): Int = R.layout.activity_splash

    override fun initData() {
        val sharedPreference = SharedPreference(this)
        val englishBufferedReadable = applicationContext.assets
            .open(Constants.FILE_NAME_EV)
            .bufferedReader()
        val vietnameseBufferedReadable = applicationContext.assets
            .open(Constants.FILE_NAME_VE)
            .bufferedReader()

        val viewModelFactory = SplashViewModelFactory(
            EnglishWordsRepository.getInstance(
                EnglishWordsLocalDataSource.getInstance(
                    englishBufferedReadable,
                    WordDatabase.getInstance(this).englishWordDao,
                    sharedPreference
                )
            ),
            VietnameseWordsRepository.getInstance(
                VietnameseWordsLocalDataSource.getInstance(
                    vietnameseBufferedReadable,
                    WordDatabase.getInstance(this).vietnameseWordDao,
                    sharedPreference
                )
            )
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SplashViewModel::class.java)

        viewModel.isWordsInsertionFinished.observe(this, Observer { finished ->
            if (finished) navigateToHomeScreen()
        })
        binding.viewModel = viewModel

    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    private fun navigateToHomeScreen() {
        HomeActivity.getIntent(this).apply {
            startActivity(this)
        }
        finish()
    }

}
