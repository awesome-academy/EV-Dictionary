package com.sun.ev_dictionary.ui.common.toeic

import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseFragment
import com.sun.ev_dictionary.databinding.FragmentCommonSearchBinding

class ToeicFragment : BaseFragment<FragmentCommonSearchBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_common_search

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        @JvmStatic
        fun newInstance() = ToeicFragment()
    }
}
