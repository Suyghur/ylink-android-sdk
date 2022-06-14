package cn.yyxx.ylink.core.network.rpc

import android.content.Context
import cn.yyxx.ylink.core.utils.Logger
import io.grpc.ManagedChannel
import io.grpc.android.AndroidChannelBuilder
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.withContext
import java.io.Closeable
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * @author #Suyghur.
 * Created on 2022/6/9
 */
class RpcService(private val context: Context, private val observer: StreamObserver<FlowsrvProto.CommandResp>) : Closeable {

    private val url by lazy {
        URL("http://127.0.0.1:10200")
    }
    private val channel: ManagedChannel by lazy {
        AndroidChannelBuilder.forAddress(url.host, url.port)
            .context(context)
            .usePlaintext()
            .keepAliveWithoutCalls(false)
            .executor(Dispatchers.IO.asExecutor())
            .build()
    }
    private val service = FlowsrvGrpcKt.FlowsrvCoroutineStub(channel)

    suspend fun connect(type: Int, token: String) {
        val bean = FlowsrvProto.CommandReq.newBuilder()
            .setType(type)
            .setAccessToken(token)
            .build()
        kotlin.runCatching {
            service.connect(bean).collect {
                withContext(Dispatchers.Main) {
                    observer.onNext(it)
                }
            }
        }.onFailure {
            Logger.e(it.message)
            if (it.message == "UNAVAILABLE: Channel shutdownNow invoked") {
                observer.onCompleted()
            } else {
                it.printStackTrace()
            }
        }
    }

    override fun close() {
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS)
//        channel.shutdownNow()
    }
}