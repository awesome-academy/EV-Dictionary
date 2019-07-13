package com.sun.ev_dictionary.ui.common.irregular

import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.base.BaseFragment
import com.sun.ev_dictionary.databinding.FragmentCommonSearchBinding

class IrregularFragment : BaseFragment<FragmentCommonSearchBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_common_search

    override fun initData() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = IrregularFragment()
    }
}
