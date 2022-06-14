package cn.yyxx.ylink.ui.entity

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

/**
 * @author #Suyghur.
 * Created on 2022/6/10
 */
data class MessageBean(
    @SerializedName("create_time")
    val createTime: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("pic")
    val pic: String,
    @SerializedName("receiver_id")
    val receiverId: String,
    @SerializedName("sender_id")
    val senderId: String,
    @SerializedName("game_id")
    val gameId: String,
    @SerializedName("uid")
    val uid: String,
    override var itemType: Int
) : MultiItemEntity