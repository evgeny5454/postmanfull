package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.evgeny_m.data.models.User
import com.evgeny_m.data.repository.userName
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentChangeUserNameBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.hideKeyboard
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton
import com.evgeny_m.postman.presentation.utils.AppTextWatcher
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory

class ChangeUserNameFragment : Fragment() {

    private lateinit var binding: FragmentChangeUserNameBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var viewModelSettings: SettingsViewModel
    private lateinit var userId: String
    private lateinit var userFirstName: String
    private lateinit var userLastName: String
    private lateinit var userPhotoUrl: String
    private lateinit var phoneNumber: String
    private lateinit var userBio: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeUserNameBinding.inflate(layoutInflater)
        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(requireContext())
        )[SettingsViewModel::class.java]
        initBackButton(binding.toolbar)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if(it.last().userName == "null") binding.userName.setText("")
            else binding.userName.setText(it.last().userName)
            userId = it.last().id
            userPhotoUrl = it.last().photoUrl
            phoneNumber = it.last().phoneNumber
            userBio = it.last().bio
            userFirstName = it.last().firstName
            userLastName = it.last().lastName
        })

        binding.userName.addTextChangedListener(AppTextWatcher {
            val string = binding.userName.text.toString()
            if (string.isNotEmpty()) {
                viewModelSettings.checkUserName(string)
            }
        })
        userName.observe(viewLifecycleOwner, Observer {
            if (!it) {
                Toast.makeText(requireContext(), "User name is already taken", Toast.LENGTH_LONG).show()
                binding.toolbar.menu.clear()

            } else {
                binding.toolbar.menu.clear()
                binding.toolbar.inflateMenu(R.menu.save)
            }
        })

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    val userName = binding.userName.text.toString()
                    val user = User(
                        userId,
                        userFirstName,
                        userLastName,
                        userName,
                        userBio,
                        userPhotoUrl,
                        phoneNumber
                    )
                    viewModelSettings.editUserName(userName)
                    if (userName.isNotEmpty()){
                        userViewModel.updateUser(user)
                    }
                    hideKeyboard()
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

}