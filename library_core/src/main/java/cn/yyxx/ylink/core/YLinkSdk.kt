package cn.yyxx.ylink.core

import android.content.Context
import cn.yyxx.ylink.core.intenal.ICallback
import cn.yyxx.ylink.core.network.api.ApiService
import cn.yyxx.ylink.core.network.rpc.FlowsrvProto
import cn.yyxx.ylink.core.network.rpc.RpcService
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author #Suyghur.
 * Created on 2022/03/22
 */
class YLinkSdk private constructor() {

    companion object {
        @JvmStatic
        fun getInstance(): YLinkSdk {
            return YLinkSdkHolder.INSTANCE
        }

        private object YLinkSdkHolder {
            val INSTANCE = YLinkSdk()
        }

        /**
         * 防止单例对象在反序列化时重新生成对象
         */
        private fun readResolve(): Any {
            return YLinkSdkHolder.INSTANCE
        }
    }

    private var rpcService: RpcService? = null

    fun auth(type: Int, gameId: String, uid: String, callback: ICallback) {
        ApiService.auth(type, gameId, uid, callback)
    }

    fun connect(context: Context, type: Int, token: String, callback: ICallback) {
        rpcService = RpcService(context, object : StreamObserver<FlowsrvProto.CommandResp> {
            override fun onNext(value: FlowsrvProto.CommandResp?) {
                value?.apply {
                    callback.onResult(code.toInt(), data.toStringUtf8())
                }
            }

            override fun onError(t: Throwable?) {
                callback.onResult(-1, "rpc stream has some error: ${t?.message}")
            }

            override fun onCompleted() {
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            rpcService?.connect(type, token)
        }
    }

    fun disconnect() {
        rpcService?.close()
        rpcService = null
    }
}