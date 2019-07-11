package com.sun.ev_dictionary.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ActivityUtils {
    companion object {
        @JvmStatic
        fun replaceFragment(
            fragmentManager: FragmentManager,
            container: Int,
            fragment: Fragment
        ) {
            fragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }
}
