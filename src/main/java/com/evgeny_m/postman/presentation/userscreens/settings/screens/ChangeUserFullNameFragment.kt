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
import com.evgeny_m.postman.databinding.FragmentChangeUserFullNameBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.hideKeyboard
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory


class ChangeUserFullNameFragment : Fragment() {

    private lateinit var binding: FragmentChangeUserFullNameBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var viewModelSettings: SettingsViewModel
    private lateinit var userId: String
    private lateinit var userName: String
    private lateinit var userPhotoUrl: String
    private lateinit var phoneNumber: String
    private lateinit var userBio: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeUserFullNameBinding.inflate(layoutInflater)
        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(requireContext())
        )[SettingsViewModel::class.java]
        initBackButton(binding.toolbar)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.last().firstName == "null") binding.editFirstName.setText("")
            else binding.editFirstName.setText(it.last().firstName)
            if (it.last().lastName == "null") binding.editLastName.setText("")
            else binding.editLastName.setText(it.last().lastName)
            userId = it.last().id
            userName = it.last().userName
            userPhotoUrl = it.last().photoUrl
            phoneNumber = it.last().phoneNumber
            userBio = it.last().bio
        })
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    val firstName = binding.editFirstName.text.toString()
                    val lastName = binding.editLastName.text.toString()
                    val user = User(
                        userId,
                        firstName,
                        lastName,
                        userName,
                        userBio,
                        userPhotoUrl,
                        phoneNumber
                    )
                    viewModelSettings.editFullName(firstName = firstName, lastName = lastName)
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