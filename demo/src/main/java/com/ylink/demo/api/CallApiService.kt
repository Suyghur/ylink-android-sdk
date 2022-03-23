package com.ylink.demo.api

import com.yyxx.yim.demo.grpc.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import java.net.URL

/**
 * @author #Suyghur.
 * Created on 2022/03/22
 */
class CallApiService {

    private lateinit var channel: ManagedChannel
    private lateinit var service: CallGrpcKt.CallCoroutineStub

    fun startChannel() {
        val url = URL("http://172.16.0.69:3000")
        val builder = ManagedChannelBuilder.forAddress(url.host, url.port)
        builder.usePlaintext()
        builder.keepAliveWithoutCalls(true)
        channel = builder.build()
        service = CallGrpcKt.CallCoroutineStub(channel)
    }

    suspend fun login(observer: StreamObserver<ClientMsgRes>) {
        kotlin.runCatching {
            val cmd = CommandMsg.newBuilder().build()
            val idInfo = IdInfo.newBuilder().build()
            val bean = ClientMsgReq.newBuilder()
                .setIdInfo(idInfo)
                .addCmd(cmd)
                .build()

            service.clientLogin(bean).collect {
                observer.onNext(it)
            }
        }.onFailure {
            it.printStackTrace()
        }

    }

    fun stop() {
        channel.shutdownNow()
    }
}