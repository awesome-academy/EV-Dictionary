package com.sun.ev_dictionary.utils

import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_EG_EN
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_EG_VI
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_ENDLINE_1
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_ENDLINE_2
import com.sun.ev_dictionary.utils.Constants.WORD_REGEX_ENDLINE_3
import com.sun.ev_dictionary.utils.Constants.WORD_REPLACE_EG_EN
import com.sun.ev_dictionary.utils.Constants.WORD_REPLACE_EG_VI
import com.sun.ev_dictionary.utils.Constants.WORD_REPLACE_ENDLINE

object StringUtils {
    fun replaceChars(string: String): String {
        return string
            .replace(WORD_REGEX_EG_EN, WORD_REPLACE_EG_EN)
            .replace(WORD_REGEX_EG_VI, WORD_REPLACE_EG_VI)
            .replace(WORD_REGEX_ENDLINE_1, WORD_REPLACE_ENDLINE)
            .replace(WORD_REGEX_ENDLINE_2, WORD_REPLACE_ENDLINE)
            .replace(WORD_REGEX_ENDLINE_3, WORD_REPLACE_ENDLINE)
    }
}
