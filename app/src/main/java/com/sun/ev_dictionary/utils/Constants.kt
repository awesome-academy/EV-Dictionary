package com.sun.ev_dictionary.utils

object Constants {
    const val FILE_NAME_EV = "ev.txt"
    const val FILE_NAME_VE = "ve.txt"
    const val EN_WORD_REGEX_START = "##"
    const val EN_WORD_REGEX_END_TYPE_1 = "#*"
    const val EN_WORD_REGEX_END_TYPE_2 = "#-"
    const val EN_WORD_REGEX_ENDLINE_1 = "|*"
    const val EN_WORD_REGEX_ENDLINE_2 = "|"
    const val WORD_REPLACE_ENDLINE = "<p>"
    const val EN_WORD_REGEX_EG_EN = "|="
    const val EN_WORD_REGEX_EG_VI = "|+"
    const val EN_WORD_REPLACE_EG_EN = "<p><b>E.g. </b>"
    const val EN_WORD_REPLACE_EG_VI = "<p><b>=></b>"
    const val PREF_ENGLISH_WORDS = "PREF_ENGLISH_WORDS"
    const val PREF_VIETNAMESE_WORDS = "PREF_VIETNAMESE_WORDS"
    const val VI_WORD_REGEX_START = "#"
    const val VI_WORD_REGEX_TYPE_1_END = "##*"
    const val VI_WORD_REGEX_TYPE_2_END = "##-"
    const val VI_WORD_REGEX_TYPE_3_END = "##"
}
