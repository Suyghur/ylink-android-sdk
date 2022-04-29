package com.ylink.demo.api

import cn.yyxx.ylink.core.utils.Logger
import com.ylink.demo.grpc.CommandReq
import com.ylink.demo.grpc.CommandResp
import com.ylink.demo.grpc.ConnectType
import com.ylink.demo.grpc.RpcbffGrpcKt
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext
import java.net.URL

class RpcbffApiService {

    private lateinit var channel: ManagedChannel
    private lateinit var service: RpcbffGrpcKt.RpcbffCoroutineStub
    private lateinit var observer: StreamObserver<CommandResp>

    fun create(observer: StreamObserver<CommandResp>) {
        val url = URL("http://172.16.0.69:10200")
        this.observer = observer
        this.channel = ManagedChannelBuilder.forAddress(url.host, url.port)
            .usePlaintext()
            .keepAliveWithoutCalls(true)
            .build()
        this.service = RpcbffGrpcKt.RpcbffCoroutineStub(channel)
    }

    suspend fun connect() {
        kotlin.runCatching {
            val bean = CommandReq.newBuilder()
                .setType(ConnectType.PLAYER)
                .setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTE3MzA3ODIsImdhbWVfaWQiOiJnYW1lMTIzMSIsImlhdCI6MTY1MTEyNTk4MiwicGxheWVyX2lkIjoidGVzdDEyMzEifQ.4VkfQy-ea7WkR_yBe1vWUFlEjve6v190SQ53Nzv5HMU")
                .build()

            service.connect(bean).collect {
                withContext(Dispatchers.Main) {
                    observer.onNext(it)
                }
            }
        }.onFailure {
            it.printStackTrace()
            Logger.e(it.message)
        }
    }

    suspend fun disconnect() {
        kotlin.runCatching {
            val bean = CommandReq.newBuilder()
                .setType(ConnectType.PLAYER)
                .setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTE3MzA3ODIsImdhbWVfaWQiOiJnYW1lMTIzMSIsImlhdCI6MTY1MTEyNTk4MiwicGxheWVyX2lkIjoidGVzdDEyMzEifQ.4VkfQy-ea7WkR_yBe1vWUFlEjve6v190SQ53Nzv5HMU")
                .build()

            flow {
                emit(service.disconnect(bean))
            }.flowOn(Dispatchers.IO)
                .onCompletion {
                    channel.shutdownNow()
                }
                .collect {
                    Logger.d(it.toString())
                }
        }.onFailure {
            it.printStackTrace()
            Logger.e(it.message)
        }
    }
}