package com.sun.ev_dictionary.ui.home.dialog_important_word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sun.ev_dictionary.R
import com.sun.ev_dictionary.ui.common.CommonAction
import com.sun.ev_dictionary.ui.common.CommonAction.*
import com.sun.ev_dictionary.ui.common.CommonActivity
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
        textEssential.setOnClickListener { chooseAction(ESSENTIAL) }
        textIrregular.setOnClickListener { chooseAction(IRREGULAR) }
        textToeic.setOnClickListener { chooseAction(TOEIC) }
        textIelts.setOnClickListener { chooseAction(IELTS) }
        textToefl.setOnClickListener { chooseAction(TOEFL) }
    }

    private fun chooseAction(action: CommonAction) =
        startActivity(CommonActivity.getIntent(activity!!, action))

    companion object {
        @JvmStatic
        fun newInstance() = ImportantWordDialogFragment()
    }
}
