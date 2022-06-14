package com.ylink.demo

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author #Suyghur.
 * Created on 2022/6/14
 */
class YLinkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.printStackTrace()
        ARouter.init(this)
    }
}