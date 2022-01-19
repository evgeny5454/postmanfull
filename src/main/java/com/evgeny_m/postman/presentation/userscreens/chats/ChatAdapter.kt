package com.evgeny_m.postman.presentation.userscreens.chats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evgeny_m.postman.databinding.ItemUserChatBinding
import com.evgeny_m.postman.domain.domain.models.DomainChatModel

class ChatAdapter(
    private val context: Context
) : RecyclerView.Adapter<ChatAdapter.UsersViewHolder>() {

    private var chats: List<DomainChatModel> = emptyList()


    class UsersViewHolder(
        val binding: ItemUserChatBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserChatBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val chat = chats[position]
        with(holder.binding) {
            userName.text = chat.name
            userLastMessage.text = chat.lastMessage
            if (chat.newMessageCounter == 0) {
                newMessageCount.visibility = View.GONE
            } else{
                newMessageCount.visibility = View.VISIBLE
                newMessageCount.text = chat.newMessageCounter.toString()
            }
            Glide.with(context)
                .load(chat.photo)
                .thumbnail(0.33f)
                .centerCrop()
                .into(userPhoto)
        }
    }

    override fun getItemCount(): Int = chats.size

    fun addChats(listChats: List<DomainChatModel>) {
        chats = listChats
        notifyDataSetChanged()
    }
}
