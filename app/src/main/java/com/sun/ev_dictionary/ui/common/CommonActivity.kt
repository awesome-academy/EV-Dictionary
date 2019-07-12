package com.sun.ev_dictionary.ui.common

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseActivity
import com.sun.ev_dictionary.databinding.ActivityCommonSearchBinding
import com.sun.ev_dictionary.ui.common.CommonAction.*
import com.sun.ev_dictionary.ui.common.essential.EssentialFragment
import com.sun.ev_dictionary.ui.common.ielts.IeltsFragment
import com.sun.ev_dictionary.ui.common.irregular.IrregularFragment
import com.sun.ev_dictionary.ui.common.toefl.ToeflFragment
import com.sun.ev_dictionary.ui.common.toeic.ToeicFragment
import com.sun.ev_dictionary.ui.common.ve_search.VESearchFragment
import com.sun.ev_dictionary.utils.ActivityUtils

class CommonActivity : BaseActivity<ActivityCommonSearchBinding>() {

    override fun getLayoutResource(): Int = R.layout.activity_common_search

    override fun initData() {
        val action = intent?.let {
            it.getSerializableExtra(EXTRA_COMMON_ACTION) as CommonAction
        }
        replaceDetailFragment(action)
    }

    private fun replaceDetailFragment(action: CommonAction?) {
        val fragment = when (action) {
            VE_SEARCH -> VESearchFragment.newInstance()
            ESSENTIAL -> EssentialFragment.newInstance()
            IRREGULAR -> IrregularFragment.newInstance()
            IELTS -> IeltsFragment.newInstance()
            TOEIC -> ToeicFragment.newInstance()
            TOEFL -> ToeflFragment.newInstance()
            else -> null
        }
        fragment?.also { replaceFragment(it) }
    }

    private fun replaceFragment(fragment: Fragment) {
        ActivityUtils.replaceFragment(
            supportFragmentManager,
            R.id.frameContainer,
            fragment
        )
    }

    companion object {
        const val EXTRA_COMMON_ACTION = "com.sun.ev_dictionary.ui.common.EXTRA_COMMON_ACTION"

        fun getIntent(context: Context, action: CommonAction) =
            Intent(context, CommonActivity::class.java).apply {
                putExtra(EXTRA_COMMON_ACTION, action)
            }
    }
}

enum class CommonAction {
    VE_SEARCH, ESSENTIAL, IRREGULAR, IELTS, TOEIC, TOEFL
}
