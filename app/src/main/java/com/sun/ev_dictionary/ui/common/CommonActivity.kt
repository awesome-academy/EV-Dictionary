package com.sun.ev_dictionary.ui.common

import android.content.Context
import android.content.Intent
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseActivity
import com.sun.ev_dictionary.databinding.ActivityCommonSearchBinding
import com.sun.ev_dictionary.ui.common.ve_search.VESearchFragment
import com.sun.ev_dictionary.utils.ActivityUtils

class CommonActivity : BaseActivity<ActivityCommonSearchBinding>() {

    override fun getLayoutResource(): Int = R.layout.activity_common_search

    override fun initData() {
        if (intent != null) {
            val fragmentName = intent.getStringExtra(EXTRA_FRAGMENT_NAME)
            replaceDetailFragment(fragmentName)
        }
    }

    private fun replaceDetailFragment(fragmentName: String) {
        when (fragmentName) {
            VESearchFragment::class.java.simpleName -> {
                ActivityUtils.replaceFragment(
                    supportFragmentManager,
                    R.id.frameContainer,
                    VESearchFragment.newInstance()
                )
            }
        }
    }

    companion object {
        const val EXTRA_FRAGMENT_NAME = "com.sun.ev_dictionary.ui.common.EXTRA_FRAGMENT_NAME"

        fun getIntent(context: Context, fragmentName: String) =
            Intent(context, CommonActivity::class.java).apply {
                putExtra(EXTRA_FRAGMENT_NAME, fragmentName)
            }
    }
}
