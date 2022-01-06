package com.evgeny_m.postman.presentation.userscreens.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeny_m.data.repository.currentUserId

import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentChatsBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initNavButton


class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var adapter: ChatAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        initNavButton(binding.toolbar)
        adapter = ChatAdapter(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        currentUserId.observe(viewLifecycleOwner, Observer {
            if (it == "null") {
                findNavController()
                    .navigate(R.id.action_chatsFragment_to_enterPhoneNumberFragment)
            }
        })
        return binding.root
    }
}