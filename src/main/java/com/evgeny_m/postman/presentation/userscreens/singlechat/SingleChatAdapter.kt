package com.evgeny_m.postman.presentation.userscreens.singlechat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.ItemMessageBinding
import com.evgeny_m.domain.models.DomainMessageModel

interface ChatListener {
    fun selectMessage(message: DomainMessageModel)
}


class SingleChatAdapter(private val chatListener: ChatListener) :
    RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>(),
    View.OnClickListener {

    private var listMessagesCache = mutableListOf<DomainMessageModel>()

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

        view.setOnClickListener(this)

        return SingleChatHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {


        when (listMessagesCache[position].from) {
            "currentUserId" -> {
                holder.blockUserMessage.visibility = View.VISIBLE
                holder.blockReceiveMessage.visibility = View.GONE
                holder.userMassageText.text = listMessagesCache[position].textMessage
                holder.progress.visibility = View.GONE
            }
            "sendMessage" -> {
                holder.blockUserMessage.visibility = View.VISIBLE
                holder.blockReceiveMessage.visibility = View.GONE
                holder.userMassageText.text = listMessagesCache[position].textMessage
                holder.progress.visibility = View.VISIBLE
            }
            else -> {
                holder.blockUserMessage.visibility = View.GONE
                holder.blockReceiveMessage.visibility = View.VISIBLE
                holder.receivedMassageText.text = listMessagesCache[position].textMessage
            }
        }
    }

    override fun getItemCount(): Int {
        return listMessagesCache.size
    }

    fun addMessages(listMessages: List<DomainMessageModel>) {
        listMessagesCache = listMessages as MutableList<DomainMessageModel>
        notifyDataSetChanged()
    }

    fun addSendMessage(text: String) {
        val item = DomainMessageModel(
            from = "sendMessage",
            textMessage = text
        )
        listMessagesCache.add(item)
        notifyDataSetChanged()
    }

    override fun onClick(v: View?) {


    }
}