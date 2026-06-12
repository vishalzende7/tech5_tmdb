package com.vishal.data.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun getTimeStampFromDateStr(date: String): Long {
    return SimpleDateFormat("yyyy-MM-dd").parse(date)?.time ?: 0L
}