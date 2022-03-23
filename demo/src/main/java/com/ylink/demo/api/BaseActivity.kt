package com.ylink.demo.api

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bbgo.library_statusbar.NotchScreenManager
import com.ylink.demo.utils.StatusBarUtil

open class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backgroundColor = Color.parseColor("#F4F4F4")
        NotchScreenManager.getInstance().setDisplayInNotch(this)

        StatusBarUtil.setColor(this, backgroundColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(backgroundColor))
        }
        window.setBackgroundDrawable(ColorDrawable(backgroundColor))
    }
}