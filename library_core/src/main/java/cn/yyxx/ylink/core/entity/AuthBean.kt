package cn.yyxx.ylink.core.entity

import com.google.gson.annotations.SerializedName

/**
 * @author #Suyghur.
 * Created on 2022/6/13
 */
data class AuthBean(
    @SerializedName("access_token")
    val accessToken: String
)