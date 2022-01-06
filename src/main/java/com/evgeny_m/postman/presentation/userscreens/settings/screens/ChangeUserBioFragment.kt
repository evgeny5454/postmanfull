package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.evgeny_m.data.models.User
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentChangeBioBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.hideKeyboard
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory


class ChangeUserBioFragment : Fragment() {

    private lateinit var binding: FragmentChangeBioBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var viewModelSettings: SettingsViewModel
    private lateinit var userId: String
    private lateinit var userFirstName: String
    private lateinit var userLastName: String
    private lateinit var userName: String
    private lateinit var userPhotoUrl: String
    private lateinit var phoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeBioBinding.inflate(layoutInflater)
        initBackButton(binding.toolbar)
        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(requireContext())
        )[SettingsViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.last().bio == "null") binding.bio.setText("")
            else binding.bio.setText(it.last().bio)
            userId = it.last().id
            userName = it.last().userName
            userPhotoUrl = it.last().photoUrl
            phoneNumber = it.last().phoneNumber
            userFirstName = it.last().firstName
            userLastName = it.last().lastName
        })

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    val userBio = binding.bio.text.toString()
                    val user = User(
                        userId,
                        userFirstName,
                        userLastName,
                        userName,
                        userBio,
                        userPhotoUrl,
                        phoneNumber
                    )
                    viewModelSettings.editBio(userBio)
                    userViewModel.updateUser(user)
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