package br.com.btm.btmapplication.extensions

import android.widget.EditText

fun EditText.value() = this.text.toString()