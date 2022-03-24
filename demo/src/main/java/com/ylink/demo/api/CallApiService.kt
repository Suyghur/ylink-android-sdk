package com.ylink.demo.api

import cn.yyxx.ylink.core.utils.Logger
import com.ylink.demo.grpc.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.net.URL

/**
 * @author #Suyghur.
 * Created on 2022/03/22
 */
class CallApiService {

    private lateinit var channel: ManagedChannel
    private lateinit var service: CallGrpcKt.CallCoroutineStub
    private lateinit var observer: StreamObserver<ClientMsgRes>

    fun connect(observer: StreamObserver<ClientMsgRes>) {
        val url = URL("http://172.16.0.69:3000")
        this.observer = observer
        this.channel = ManagedChannelBuilder.forAddress(url.host, url.port)
            .usePlaintext()
            .keepAliveWithoutCalls(true)
            .build()
        this.service = CallGrpcKt.CallCoroutineStub(channel)
    }

    suspend fun login() {
        kotlin.runCatching {
            val cmd = CommandMsg.newBuilder().build()
            val idInfo = IdInfo.newBuilder().build()
            val bean = ClientMsgReq.newBuilder()
                .setIdInfo(idInfo)
                .addCmd(cmd)
                .build()

            service.clientLogin(bean).collect {
                withContext(Dispatchers.Main) {
                    observer.onNext(it)
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    suspend fun send(msg: String, userId: String, gmId: String) {
        kotlin.runCatching {
            val chatMsg = ChatMsg.newBuilder().setInput(msg).setClientId(userId).build()
            val idInfo = IdInfo.newBuilder().setId(userId).setGameId(1001).build()
            val cmd = CommandMsg.newBuilder().setCmdType(ECommand.CALL_PLAYER_MSG).setChatMsg(chatMsg).build()
            val bean = ClientMsgReq.newBuilder()
                .setIdInfo(idInfo)
                .addCmd(cmd)
                .build()
            service.clientCall(bean)
        }.onFailure {
            it.printStackTrace()
        }
    }

    private suspend fun call(idInfo: IdInfo, cmd: CommandMsg, observer: StreamObserver<ClientMsgRes>) {

    }

    fun disConnect() {
        channel.shutdownNow()
    }
}