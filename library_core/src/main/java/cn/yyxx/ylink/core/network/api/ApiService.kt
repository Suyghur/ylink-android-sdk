package cn.yyxx.ylink.core.network.api

import cn.yyxx.ylink.core.entity.AuthBean
import cn.yyxx.ylink.core.entity.Result
import cn.yyxx.ylink.core.intenal.ICallback
import cn.yyxx.ylink.core.network.rxhttp.RxHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rxhttp.flowOn
import rxhttp.toClass
import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

/**
 * @author #Suyghur.
 * Created on 2022/6/14
 */
object ApiService {

    @Domain(name = "AuthBff", className = "AuthBff")
    @JvmField
    val BASIC_AUTHBFF_URL = "http://127.0.0.1:10000/api/v1/auth"

    @DefaultDomain
    @JvmField
    val BASIC_CMDBFF_URL = ""

    fun auth(type: Int, gameId: String, playerId: String, callback: ICallback) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = if (type == 0) {
                RxHttp.postJson("/player-login")
                    .setDomainToAuthBffIfAbsent()
                    .add("game_id", gameId)
                    .add("player_id", playerId)
                    .toClass<Result<AuthBean>>()
                    .flowOn(Dispatchers.IO)
                    .await()
            } else {
                RxHttp.postJson("/cs-login")
                    .setDomainToAuthBffIfAbsent()
                    .add("cs_id", playerId)
                    .toClass<Result<AuthBean>>()
                    .flowOn(Dispatchers.IO)
                    .await()
            }
            if (result.code == 0) {
                callback.onResult(0, result.data.accessToken)
            } else {
                callback.onResult(-1, result.msg)
            }
        }
    }
}