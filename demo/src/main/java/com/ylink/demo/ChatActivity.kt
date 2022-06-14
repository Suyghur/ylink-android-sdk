package com.ylink.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.yyxx.ylink.core.YLinkSdk
import cn.yyxx.ylink.core.utils.Logger
import cn.yyxx.ylink.ui.adapter.MessageItemAdapter
import cn.yyxx.ylink.ui.entity.MessageBean
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.ylink.demo.databinding.ActivityChatBinding

@Route(path = "/main/chat")
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    @JvmField
    @Autowired(name = "type")
    var type = 0

    @JvmField
    @Autowired(name = "access_token")
    var accessToken = ""

    private val messageList by lazy {
        mutableListOf<MessageBean>()
    }

    private val messageItemAdapter: MessageItemAdapter by lazy {
        MessageItemAdapter(messageList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ImmersionBar.with(this).titleBar(binding.chatBarLayout).keyboardEnable(true).transparentStatusBar().init()
        ARouter.getInstance().inject(this)
        Logger.d("type: $type")
        Logger.d("accessToken: $accessToken")
        initView()

//        YLinkSdk.getInstance().connect(
//            this,
//            0L,
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTUxMTA1NDcsImdhbWVfaWQiOiJnYW1lXzEyMzEiLCJpYXQiOjE2NTQ1MDU3NDcsInBsYXllcl9pZCI6InBsYXllcl8zMzMzIn0.WX5ubKbxKJhVMbvheb8E2uQNy8TeB5JG71BggUccMlc",
//            object : ICallback {
//                override fun onResult(code: Int, result: String) {
////                    messageList.add()
//                    Logger.d("code: $code, result: $result")
//                    if (code == 0) {
//                        val message = Gson().fromJson(result, MessageBean::class.java)
//                        message.itemType = if (message.uid == playerId) {
//                            0
//                        } else {
//                            1
//                        }
//                        messageList.add(message)
//                        messageItemAdapter.notifyDataSetChanged()
//                    }
//                }
//            })
    }

    private fun initView() {
        binding.chatSwipeRefreshLayout.run {
            setColorSchemeColors(resources.getColor(R.color.ylink_color_green_blue))
            setOnRefreshListener {
                isRefreshing = false
            }
        }

        binding.chatRecyclerView.run {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messageItemAdapter
            itemAnimator = DefaultItemAnimator()
//            addItemDecoration(SpaceItemDecoration(this@ChatActivity))
        }

        binding.chatBtnSend.setOnClickListener {
            val content = binding.chatEtInput.text.toString()
//            messageList.add(
//                MessageBean(
//                    createTime = "2022-06-10 16:52:49",
//                    content = content,
//                    pic = "",
//                    receiverId = csId,
//                    senderId = "${gameId}_$playerId",
//                    gameId = gameId,
//                    uid = playerId,
//                    itemType = 0
//                )
//            )
            messageItemAdapter.notifyDataSetChanged()
            binding.chatEtInput.setText("")

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        YLinkSdk.getInstance().disconnect()
    }
}