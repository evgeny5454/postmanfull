package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.evgeny_m.data.registration.FirebaseRegistrationImpl
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.domain.usecase.RegistrationUseCase
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentSettingsBinding
import com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet.PhotoBottomSheetFragment
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton

val photoBottomSheetFragment = PhotoBottomSheetFragment()

class SettingsFragment : Fragment() {


    private lateinit var binding: FragmentSettingsBinding

    //private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }
    private lateinit var registrationUseCase: RegistrationUseCase
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        initBackButton(binding.toolbar)
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            binding.accountSettings.userNameText.text = it.last().userName
            binding.toolbar.title = it.last().firstName
            binding.accountSettings.userBioText.text = it.last().bio
        })
        /* viewModel.userName.observe(viewLifecycleOwner, Observer {
             binding.accountSettings.userNameText.text = "@$it"
         })
         viewModel.userFirstName.observe(viewLifecycleOwner, Observer {
             binding.toolbar.title = it
         })
         viewModel.bio.observe(viewLifecycleOwner, Observer {
             binding.accountSettings.userBioText.text = it
         })
         viewModel.photoUri.observe(viewLifecycleOwner, Observer {
             Glide.with(requireContext())
                 .load(it)
                 .centerCrop()
                 .into(binding.userPhotoFelid)
         })*/

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
                    registrationUseCase = RegistrationUseCase(
                        firebaseRegistration = FirebaseRegistrationImpl(requireActivity())
                    )
                    registrationUseCase.logOut()
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        //viewModel.loadUserData()
    }
}