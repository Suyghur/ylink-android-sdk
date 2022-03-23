package com.ylink.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ylink.demo.api.BaseActivity
import com.ylink.demo.databinding.ActivityChatBinding

class ChatActivity : BaseActivity() {

    private lateinit var binding: ActivityChatBinding

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ChatActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}