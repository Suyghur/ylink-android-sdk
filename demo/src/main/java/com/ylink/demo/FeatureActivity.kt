package com.ylink.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author #Suyghur.
 * Created on 2022/6/14
 */
@Route(path = "/main/feature")
class FeatureActivity : AppCompatActivity() {

    @JvmField
    @Autowired(name = "type")
    var type = 0

    @JvmField
    @Autowired(name = "access_token")
    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}