package com.sun.ev_dictionary.ui.splash

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseActivity
import com.sun.ev_dictionary.data.source.local.EnglishWordDatabase
import com.sun.ev_dictionary.data.source.local.EnglishWordsLocalDataSource
import com.sun.ev_dictionary.data.source.repository.EnglishWordsRepository
import com.sun.ev_dictionary.databinding.ActivitySplashBinding
import com.sun.ev_dictionary.ui.home.HomeActivity
import com.sun.ev_dictionary.utils.Constants
import com.sun.ev_dictionary.utils.SharedPreference

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var viewModelFactory: SplashViewModelFactory
    private lateinit var sharedPreference: SharedPreference
    override fun getLayoutResource(): Int = R.layout.activity_splash

    override fun initData() {
        sharedPreference = SharedPreference(this)
        val bufferedReadable = applicationContext.assets
            .open(Constants.FILE_NAME_EV)
            .bufferedReader()
        viewModelFactory = SplashViewModelFactory(
            EnglishWordsRepository.getInstance(
                EnglishWordsLocalDataSource.getInstance(
                    bufferedReadable,
                    EnglishWordDatabase.getInstance(this).englishWordDao,
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

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun navigateToHomeScreen() {
        HomeActivity.getIntent(this).apply {
            startActivity(this)
        }
        finish()
    }

}
