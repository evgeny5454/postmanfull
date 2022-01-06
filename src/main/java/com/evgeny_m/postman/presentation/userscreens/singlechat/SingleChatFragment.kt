package com.evgeny_m.postman.presentation.userscreens.singlechat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeny_m.postman.databinding.FragmentSingleChatBinding
import com.evgeny_m.domain.models.DomainMessageModel
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton


class SingleChatFragment : Fragment() {

    private lateinit var binding: FragmentSingleChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleChatBinding.inflate(layoutInflater)
        initBackButton(binding.toolbar)
        val adapter = SingleChatAdapter(object : ChatListener {
            override fun selectMessage(message: DomainMessageModel) {
                //Toast.makeText(requireContext(), message.textMessage, Toast.LENGTH_SHORT).show()
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        /*viewModel.messages.observe(viewLifecycleOwner, Observer {
            adapter.addMessages(it)
        })*/
        binding.buttonSend.setOnClickListener {
            val text = binding.messageText.text.toString()
            adapter.addSendMessage(text)
            //viewModel.sendMessage(text)
            binding.messageText.setText("")
        }
       /* viewModel.result.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.loadMessages()
            }
        })*/

        return binding.root
    }


}