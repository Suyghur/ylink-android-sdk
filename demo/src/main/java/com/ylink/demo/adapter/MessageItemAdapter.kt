package com.ylink.demo.adapter

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ylink.demo.R
import com.ylink.demo.bean.MessageItemBean

/**
 * @author #Suyghur.
 * Created on 2021/11/03
 */
class MessageItemAdapter(dataset: MutableList<MessageItemBean>) : BaseQuickAdapter<MessageItemBean, BaseViewHolder>(R.layout.item_message_list, dataset) {
    override fun convert(holder: BaseViewHolder, item: MessageItemBean) {
        if (item.type == 1) {
            holder.getView<ConstraintLayout>(R.id.item_message_cl_system).visibility = View.GONE
            holder.getView<ConstraintLayout>(R.id.item_message_cl_me).visibility = View.VISIBLE
            holder.setText(R.id.item_message_tv_me_content, item.msg)
        } else {
            holder.getView<ConstraintLayout>(R.id.item_message_cl_system).visibility = View.VISIBLE
            holder.getView<ConstraintLayout>(R.id.item_message_cl_me).visibility = View.GONE
            holder.setText(R.id.item_message_tv_content, item.msg)
        }
    }
}