package com.ylink.demo

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.ylink.demo.api.BaseActivity
import com.ylink.demo.databinding.ActivityMainBinding

/**
 * @author #Suyghur.
 * Created on 2022/03/22
 */
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var guestModel = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.mainBtnGuest.setOnClickListener {
            guestModel = true
            ChatActivity.start(this)
        }


        binding.mainBtnUser.setOnClickListener {
            val uid = binding.mainEtUid.text.toString()
            val gameid = binding.mainEtGameid.text.toString()
            if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(gameid)) {
                return@setOnClickListener
            }
            ChatActivity.start(this)
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
