package com.sun.ev_dictionary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<BD : ViewDataBinding> : Fragment() {
    protected lateinit var binding: BD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    @LayoutRes
    abstract fun getLayoutResource(): Int

    abstract fun initData()

}
