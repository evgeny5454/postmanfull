package com.evgeny_m.postman.presentation.userscreens.singlechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evgeny_m.data.models.MessageModel
import com.evgeny_m.data.repository.currentUserId
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.ItemMessageBinding
import com.evgeny_m.domain.models.DomainMessageModel

interface ChatListener {
    fun selectMessage(message: DomainMessageModel)
}


class SingleChatAdapter :
    RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var listMessagesCache = mutableListOf<MessageModel>()

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMessageBinding.bind(view)

        val blockUserMessage = binding.viewSendTextMessage
        val userMassageText = binding.sendMessageText
        val progress = binding.progress

        val blockReceiveMessage = binding.viewReceivedTextMessage
        val receivedMassageText = binding.receivedMessageText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return SingleChatHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {


        when (listMessagesCache[position].from) {
            currentUserId.value.toString() -> {
                holder.blockUserMessage.visibility = View.VISIBLE
                holder.blockReceiveMessage.visibility = View.GONE
                holder.userMassageText.text = listMessagesCache[position].messageText
                holder.progress.visibility = View.GONE
            }
            "sendMessage" -> {
                holder.blockUserMessage.visibility = View.VISIBLE
                holder.blockReceiveMessage.visibility = View.GONE
                holder.userMassageText.text = listMessagesCache[position].messageText
                holder.progress.visibility = View.VISIBLE
            }
            else -> {
                holder.blockUserMessage.visibility = View.GONE
                holder.blockReceiveMessage.visibility = View.VISIBLE
                holder.receivedMassageText.text = listMessagesCache[position].messageText
            }
        }
    }

    override fun getItemCount(): Int {
        return listMessagesCache.size
    }

    fun addMessages(listMessages: List<MessageModel>) {
        listMessagesCache = listMessages as MutableList<MessageModel>
        notifyDataSetChanged()
    }

    /*fun addSendMessage(text: String) {
        val item = DomainMessageModel(
            from = "sendMessage",
            textMessage = text
        )
        listMessagesCache.add(item)
        notifyDataSetChanged()
    }*/

    /*override fun onClick(v: View?) {


    }*/
}