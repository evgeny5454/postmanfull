package com.evgeny_m.postman.presentation.userscreens.singlechat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.evgeny_m.postman.data.repository.dataUpdate
import com.evgeny_m.postman.data.repository.messages
import com.evgeny_m.postman.databinding.FragmentSingleChatBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButtonToChats
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory


class SingleChatFragment : Fragment() {

    private lateinit var viewModelSettings: SettingsViewModel
    private lateinit var binding: FragmentSingleChatBinding
    private val args by navArgs<SingleChatFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleChatBinding.inflate(layoutInflater)
        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(requireContext())
        )[SettingsViewModel::class.java]
        initBackButtonToChats(binding.toolbar)
        val adapter = SingleChatAdapter()
        Glide.with(requireActivity())
            .load(args.currentUser.photo)
            .thumbnail(0.33f)
            .centerCrop()
            .into(binding.userPhoto)

        binding.userName.text = args.currentUser.name
        binding.status.text = args.currentUser.status

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter


        binding.buttonSend.setOnClickListener {
            val text = binding.messageText.text.toString()
            viewModelSettings.sendMessage(text, args.currentUser.id.toString())
            binding.messageText.setText("")
        }

        dataUpdate.observe(viewLifecycleOwner, Observer {
            if (it) {
                //Toast.makeText(requireContext(), "Сообщения обновлены", Toast.LENGTH_LONG).show()
                viewModelSettings.loadMessages(args.currentUser.id.toString())
            } else {
                Toast.makeText(requireContext(), "Невозможно обновить цепочку сообщений", Toast.LENGTH_LONG).show()
            }
        })

        messages.observe(viewLifecycleOwner, Observer {
            adapter.addMessages(it)
        })


        return binding.root
    }


}