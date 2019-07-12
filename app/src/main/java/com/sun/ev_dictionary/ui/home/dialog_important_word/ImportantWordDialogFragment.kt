package com.sun.ev_dictionary.ui.home.dialog_important_word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.ui.common.CommonActivity
import com.sun.ev_dictionary.ui.common.essential.EssentialFragment
import com.sun.ev_dictionary.ui.common.ielts.IeltsFragment
import com.sun.ev_dictionary.ui.common.irregular.IrregularFragment
import com.sun.ev_dictionary.ui.common.toefl.ToeflFragment
import com.sun.ev_dictionary.ui.common.toeic.ToeicFragment
import kotlinx.android.synthetic.main.fragment_dialog_home_menu.*

class ImportantWordDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_dialog_home_menu,
            container,
            false
        )
    }

    override fun onStart() {
        super.onStart()
        setEventClick()
    }

    private fun setEventClick() {
        textEssential.setOnClickListener { chooseEssentialWord() }
        textIrregular.setOnClickListener { chooseIrregularWord() }
        textToeic.setOnClickListener { chooseTOEICWord() }
        textIelts.setOnClickListener { chooseIELTSWord() }
        textToefl.setOnClickListener { chooseTOEFLWord() }
    }

    private fun chooseEssentialWord() {
        startActivity(
            CommonActivity.getIntent(activity!!, EssentialFragment::class.java.simpleName)
        )
    }

    private fun chooseIrregularWord() {
        startActivity(
            CommonActivity.getIntent(activity!!, IrregularFragment::class.java.simpleName)
        )
    }

    private fun chooseTOEICWord() {
        startActivity(
            CommonActivity.getIntent(activity!!, ToeicFragment::class.java.simpleName)
        )
    }

    private fun chooseIELTSWord() {
        startActivity(
            CommonActivity.getIntent(activity!!, IeltsFragment::class.java.simpleName)
        )
    }

    private fun chooseTOEFLWord() {
        startActivity(
            CommonActivity.getIntent(activity!!, ToeflFragment::class.java.simpleName)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImportantWordDialogFragment()
    }
}
