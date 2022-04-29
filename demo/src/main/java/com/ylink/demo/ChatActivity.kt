package com.ylink.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.yyxx.ylink.core.utils.Logger
import com.gyf.immersionbar.ImmersionBar
import com.ylink.demo.adapter.MessageItemAdapter
import com.ylink.demo.api.RpcbffApiService
import com.ylink.demo.bean.MessageItemBean
import com.ylink.demo.databinding.ActivityChatBinding
import com.ylink.demo.grpc.CommandResp
import com.ylink.demo.widget.SpaceItemDecoration
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val apiService by lazy { RpcbffApiService() }
    private var userId = ""
    private var gmId = ""

    private val messageList = mutableListOf<MessageItemBean>()

    private val mAdapter: MessageItemAdapter by lazy {
        MessageItemAdapter(messageList)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ChatActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImmersionBar.with(this).titleBar(binding.chatBarLayout).keyboardEnable(true).transparentStatusBar().init()

        initView()

        apiService.create(object : StreamObserver<CommandResp> {
            override fun onNext(value: CommandResp?) {
                value?.apply {
                    Logger.d(this.toString())
//                    when (commandMsg.cmdType) {
//                        ECommand.SEND_MSG -> {
//                            messageList.add(MessageItemBean(0, commandMsg.chatMsg.input))
//                            mAdapter.notifyDataSetChanged()
//                        }
//                        ECommand.ON_PLAYER_CONNECT -> userId = commandMsg.cmdStr
//                        ECommand.ON_PLAYER_RECEIVE_REPLY -> gmId = commandMsg.cmdStr
////                        ECommand.CALL_PLAYER_MSG->
//                    }
                }
            }

            override fun onError(t: Throwable?) {
            }

            override fun onCompleted() {
            }
        })
        lifecycleScope.launch(Dispatchers.IO) {
            apiService.connect()
        }
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
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(SpaceItemDecoration(this@ChatActivity))
        }

        binding.chatBtnSend.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val msg = binding.chatEtInput.text.toString()
//                apiService.send(msg, userId, gmId)
//                withContext(Dispatchers.Main) {
//                    binding.chatEtInput.setText("")
//                    messageList.add(MessageItemBean(1, msg))
//                    mAdapter.notifyDataSetChanged()
//                }
//            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.IO).launch {
            apiService.disconnect()
        }
    }
}