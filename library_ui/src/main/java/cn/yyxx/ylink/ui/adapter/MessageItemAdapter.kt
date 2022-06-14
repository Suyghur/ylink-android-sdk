package cn.yyxx.ylink.ui.adapter

import cn.yyxx.ylink.ui.R
import cn.yyxx.ylink.ui.entity.MessageBean
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author #Suyghur.
 * Created on 2022/6/10
 */
class MessageItemAdapter(dataset: MutableList<MessageBean>) : BaseMultiItemQuickAdapter<MessageBean, BaseViewHolder>(dataset) {

    init {
        addItemType(0, R.layout.ylink_item_msg_send)
        addItemType(1, R.layout.ylink_item_msg_receive)
    }

    override fun convert(holder: BaseViewHolder, item: MessageBean) {
        when (holder.itemViewType) {
            0 -> holder.setText(R.id.ylink_tv_content, "发送信息: ${item.content}")
            1 -> holder.setText(R.id.ylink_tv_content, "收到信息: ${item.content}")
        }
    }
}