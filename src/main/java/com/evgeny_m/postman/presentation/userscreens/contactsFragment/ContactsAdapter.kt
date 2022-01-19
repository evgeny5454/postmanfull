package com.evgeny_m.postman.presentation.userscreens.contactsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.ItemContactBinding

class ContactsAdapter(private val context: Context): RecyclerView.Adapter<ContactsAdapter.ContactsHolder>() {

    private var listContactsCache = emptyList<com.evgeny_m.postman.data.models.UserData>()

    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemContactBinding.bind(view)
        val photo = binding.photo
        val name = binding.name
        val status = binding.status
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactsHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsHolder, position: Int) {
        val currentItem = listContactsCache[position]
        holder.name.text = listContactsCache[position].name
        holder.status.text = listContactsCache[position].status

        Glide.with(context)
            .load(listContactsCache[position].photo)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.photo)

        holder.binding.userDetails.setOnClickListener {
            val action = ContactsFragmentDirections.actionContactsFragmentToContactDetailsFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
        holder.binding.contact.setOnClickListener {
            val action = ContactsFragmentDirections.actionContactsFragmentToSingleChatFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return listContactsCache.size
    }

    fun addContacts(list: List<com.evgeny_m.postman.data.models.UserData>) {
        listContactsCache = list
        notifyDataSetChanged()
    }
}


