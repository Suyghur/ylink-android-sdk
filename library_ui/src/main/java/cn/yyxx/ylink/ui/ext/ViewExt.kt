package cn.yyxx.ylink.ui.ext

import android.view.View

/**
 * @author #Suyghur.
 * Created on 2022/02/15
 */
inline fun View.setThrottleListener(delayMillis: Long = 1000L, crossinline onClick: () -> Unit) {
    this.setOnClickListener {
        this.isClickable = false
        onClick()
        this.postDelayed({
            this.isClickable = true
        }, delayMillis)
    }
}