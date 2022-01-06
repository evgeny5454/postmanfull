package com.evgeny_m.postman.presentation.userscreens.registrationFragment

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.evgeny_m.data.models.User
import com.evgeny_m.data.repository.FirebaseRepository
import com.evgeny_m.data.repository.currentUserId
import com.evgeny_m.data.repository.userData
import com.evgeny_m.data.repository.verificationId
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.domain.usecase.register.CheckUserDataUseCase
import com.evgeny_m.domain.usecase.register.EnterCodeUseCase
import com.evgeny_m.domain.usecase.register.EnterPhoneNumberUseCase
import com.evgeny_m.postman.databinding.FragmentRegistrationBinding
import com.evgeny_m.postman.presentation.utils.AppTextWatcher
import com.evgeny_m.postman.presentation.utils.hideKeyboard
import com.evgeny_m.postman.presentation.viewModels.register.RegisterViewModel
import com.evgeny_m.postman.presentation.viewModels.register.RegisterViewModelFactory


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var stringPhone: String
    private lateinit var userId: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        registerViewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(requireContext())
        )[RegisterViewModel::class.java]
        binding.userPhone.addTextChangedListener(AppTextWatcher {
            stringPhone = binding.userPhone.text.toString()
            if (stringPhone.length == 12) {
                hideKeyboard()
                showAlertEnterPhoneDialog(stringPhone)
            }
        })
        binding.smsCode.visibility = View.INVISIBLE
        currentUserId.observe(viewLifecycleOwner, Observer {

            if (it != "null") {
                userId = it
                registerViewModel.checkData(userId)
            }
        })

        userData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val user = User(
                    id = userId,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    userName = it.userName,
                    bio = it.bio,
                    photoUrl = it.userPhotoUri,
                    phoneNumber = stringPhone
                )
                userViewModel.addUser(user)
            }
        })

        verificationId.observe(viewLifecycleOwner, Observer {
            binding.smsCode.visibility = View.VISIBLE
            binding.smsCode.addTextChangedListener(AppTextWatcher {
                val stringSms = binding.smsCode.text.toString()
                if (stringSms.length == 6) {
                    binding.smsCode.focusable = View.NOT_FOCUSABLE
                    hideKeyboard()
                    registerViewModel.enterCode(stringSms)
                    Toast.makeText(requireContext(), "Sms code passed", Toast.LENGTH_LONG).show()
                }
            })
        })
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                findNavController().popBackStack()
            }
        })
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAlertEnterPhoneDialog(string: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            binding.userPhone.focusable = View.NOT_FOCUSABLE
            registerViewModel.enterPhone(string)
        }
        builder.setNegativeButton("No") { _, _ ->
            //nothing to do
        }
        builder.setTitle("Number is correct?")
        builder.setMessage(string)
        builder.create().show()
    }
}