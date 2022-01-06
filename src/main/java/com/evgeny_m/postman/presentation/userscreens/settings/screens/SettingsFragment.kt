package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.evgeny_m.data.repository.dataUpdate
import com.evgeny_m.data.repository.updateData

import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentSettingsBinding
import com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet.PhotoBottomSheetFragment
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory

val photoBottomSheetFragment = PhotoBottomSheetFragment()

class SettingsFragment : Fragment() {


    private lateinit var binding: FragmentSettingsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var viewModelSettings: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(requireContext())
        )[SettingsViewModel::class.java]
        initBackButton(binding.toolbar)

        updateData.observe(viewLifecycleOwner, Observer {
            if (it == "true") {
                Toast.makeText(requireContext(), "Data update", Toast.LENGTH_LONG).show()
            } else if (it == "false") {
                Toast.makeText(requireContext(), "Data is not update", Toast.LENGTH_LONG).show()
            }
        })

        dataUpdate.observe(viewLifecycleOwner, Observer {
            if (it){
                Toast.makeText(requireContext(), "Listener is working", Toast.LENGTH_LONG).show()
            }
        })

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if(it.last().firstName == "null") binding.toolbar.title = ""
            else binding.toolbar.title = it.last().firstName
            if(it.last().userName == "null") binding.accountSettings.userName.text = ""
            else binding.accountSettings.userName.text = "@${it.last().userName}"
            if(it.last().bio == "null") binding.accountSettings.userBio.text = ""
            else binding.accountSettings.userBio.text = it.last().bio
            if(it.last().phoneNumber == "null") binding.accountSettings.userPhone.text = ""
            else binding.accountSettings.userPhone.text = it.last().phoneNumber

            Glide.with(requireContext())
                .load(it.last().photoUrl)
                .centerCrop()
                .into(binding.userPhoto)
        })
        binding.addPhoto.setOnClickListener {
            photoBottomSheetFragment.show(
                parentFragmentManager,
                PhotoBottomSheetFragment.TAG
            )
        }
        binding.accountSettings.buttonUserName.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_settingsFragment_to_changeUserNameFragment)
        }
        binding.accountSettings.buttonUserBio.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_settingsFragment_to_changeBioFragment)
        }
        binding.accountSettings.buttonUserNumber.setOnClickListener {

        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings_menu_edit_full_name -> {
                    view?.findNavController()
                        ?.navigate(R.id.action_settingsFragment_to_changeUserFullNameFragment)
                    true
                }
                R.id.settings_menu_add_photo -> {
                    photoBottomSheetFragment.show(
                        parentFragmentManager,
                        PhotoBottomSheetFragment.TAG
                    )
                    true
                }
                R.id.settings_menu_log_out -> {
                    viewModelSettings.logOut()
                    userViewModel.deleteAllUsers()
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }
}