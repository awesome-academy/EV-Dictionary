package com.sun.ev_dictionary.utils

import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_EG_EN
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_EG_VI
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_ENDLINE_1
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REGEX_ENDLINE_2
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REPLACE_EG_EN
import com.sun.ev_dictionary.utils.Constants.EN_WORD_REPLACE_EG_VI
import com.sun.ev_dictionary.utils.Constants.WORD_REPLACE_ENDLINE

object StringUtils {

    fun replaceChars(string: String): String {
        return string
            .replace(EN_WORD_REGEX_EG_EN, EN_WORD_REPLACE_EG_EN)
            .replace(EN_WORD_REGEX_EG_VI, EN_WORD_REPLACE_EG_VI)
            .replace(EN_WORD_REGEX_ENDLINE_1, WORD_REPLACE_ENDLINE)
            .replace(EN_WORD_REGEX_ENDLINE_2, WORD_REPLACE_ENDLINE)
    }
}
