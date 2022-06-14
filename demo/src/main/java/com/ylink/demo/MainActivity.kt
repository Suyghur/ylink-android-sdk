package com.ylink.demo

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import cn.yyxx.ylink.core.YLinkSdk
import cn.yyxx.ylink.core.intenal.ICallback
import cn.yyxx.ylink.ui.ext.setThrottleListener
import cn.yyxx.ylink.ui.ext.showToast
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.ylink.demo.databinding.ActivityMainBinding


/**
 * @author #Suyghur.
 * Created on 2022/03/22
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ImmersionBar.with(this).titleBar(binding.mainBarLayout).keyboardEnable(true).transparentStatusBar().init()

        binding.mainBtnGuest.setThrottleListener {
            go2FeatureActivity(0)
        }

        binding.mainBtnUser.setThrottleListener {
            go2FeatureActivity(0)
        }

        binding.mainBtnCs.setThrottleListener {
            go2FeatureActivity(1)
        }
    }

    private fun go2FeatureActivity(type: Int) {
        if (type == 0) {
            // 玩家
            val gameId = binding.mainEtGameId.text.toString()
            val playerId = binding.mainEtUid.text.toString()
            if (TextUtils.isEmpty(gameId) || TextUtils.isEmpty(playerId)) {
                showToast("游戏Id或玩家Id不能为空")
            }
            YLinkSdk.getInstance().auth(type, gameId, playerId, object : ICallback {
                override fun onResult(code: Int, result: String) {
                    if (code == 0) {
                        ARouter.getInstance().build("/main/feature")
                            .withInt("type", 0)
                            .withString("access_token", result)
                            .navigation()
                    } else {
                        showToast("code: $code, result:$result")
                    }
                }
            })
        } else {
            val csId = binding.mainEtUid.text.toString()
            if (TextUtils.isEmpty(csId)) {
                showToast("客服Id不能为空")
                return
            }
            YLinkSdk.getInstance().auth(type, "", csId, object : ICallback {
                override fun onResult(code: Int, result: String) {
                    if (code == 0) {
                        ARouter.getInstance().build("/main/feature")
                            .withInt("type", 1)
                            .withString("access_token", result)
                            .navigation()
                    } else {
                        showToast("code: $code, result:$result")
                    }
                }
            })
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
