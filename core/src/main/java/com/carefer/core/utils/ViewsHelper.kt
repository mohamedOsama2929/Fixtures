package com.carefer.core.utils


import android.annotation.SuppressLint
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

fun ImageView.loadImg(imgUrl: Any, placeHolder: Int? = null) {

    val glideImg = Glide.with(context)
        .load(imgUrl)

    placeHolder?.run {
        glideImg.placeholder(placeHolder)
            .fallback(placeHolder)
    }

    glideImg.into(this)
}

fun View.hideSoftKeyboard() {
    this.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this.context, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

@SuppressLint("ClickableViewAccessibility")
fun View.hideKeyBoardOutSideTap() {
    this.setOnTouchListener { v, event ->
        v.hideSoftKeyboard()
        false
    }
}




