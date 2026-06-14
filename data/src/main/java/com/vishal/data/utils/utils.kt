package com.vishal.data.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun getTimeStampFromDateStr(date: String): Long {
    return try {
         SimpleDateFormat("yyyy-MM-dd").parse(date)?.time ?: 0L
    } catch (e: ParseException) {
        0L
    }
}