package com.evgeny_m.postman.presentation.userscreens.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeny_m.data.chatsfirebase.result
import com.evgeny_m.data.models.User
import com.evgeny_m.data.registration.FirebaseRegistrationImpl
import com.evgeny_m.data.registration.currentUserId
import com.evgeny_m.data.viewmodels.AuthViewModel
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.domain.usecase.RegistrationUseCase
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentChatsBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initNavButton


class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    //private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }
    private lateinit var adapter: ChatAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var registrationUseCase: RegistrationUseCase
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(layoutInflater)

        /*registrationUseCase = RegistrationUseCase(
            firebaseRegistration = FirebaseRegistrationImpl(requireActivity())
        )*/
        //registrationUseCase.getUserId()
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        initNavButton(binding.toolbar)
        adapter = ChatAdapter(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.userId.observe(viewLifecycleOwner, Observer {
            if (it == "null") {
                view?.findNavController()
                    ?.navigate(R.id.action_chatsFragment_to_enterPhoneNumberFragment)
            }
        })

        /*currentUserId.observe(viewLifecycleOwner, Observer {
            if (it == "null") {
                view?.findNavController()
                    ?.navigate(R.id.action_chatsFragment_to_enterPhoneNumberFragment)
            }
        })*/
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("AAAAAAAA", "onResume ChatsFragment")

        result.observe(viewLifecycleOwner, Observer {
            when (it) {
                "isPending" -> {
                    //drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    //view?.findNavController()?.navigate(R.id.action_chatsFragment_to_splashFragment)
                    binding.progress.visibility = View.VISIBLE
                }
                "isSuccess" -> {
                    //viewModel.loadChats()
                }
                "isFail" -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), "unable to update the feed, check your internet connection", Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.progress.visibility = View.GONE
                }
            }
        })

       /* viewModel.chats.observe(viewLifecycleOwner, Observer {
           val data = it
            if (data.isNotEmpty()) {
                adapter.addChats(it)
                binding.progress.visibility = View.GONE
            }
        })*/

    }


}