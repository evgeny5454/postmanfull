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
import com.evgeny_m.data.registration.FirebaseRegistrationImpl
import com.evgeny_m.data.registration.currentUserId
import com.evgeny_m.data.registration.register
import com.evgeny_m.data.viewmodels.AuthViewModel
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.domain.usecase.RegistrationUseCase
import com.evgeny_m.postman.databinding.FragmentEnterPhoneNumberBinding
import com.evgeny_m.postman.presentation.utils.AppTextWatcher
import com.evgeny_m.postman.presentation.utils.hideKeyboard


class EnterPhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentEnterPhoneNumberBinding

    private lateinit var viewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var registrationUseCase: RegistrationUseCase
    private lateinit var stringPhone: String
    private lateinit var stringSms: String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterPhoneNumberBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        binding.phoneNumber.addTextChangedListener(AppTextWatcher {
            stringPhone = binding.phoneNumber.text.toString()
            if (stringPhone.length == 12) {
                //binding.phoneNumber.focusable = View.NOT_FOCUSABLE
                hideKeyboard()
                showAlertDialog(stringPhone)
            }
        })

        binding.smsCode.addTextChangedListener(AppTextWatcher {
            stringSms = binding.smsCode.text.toString()
            if (stringSms.length == 6) {
                binding.smsCode.focusable = View.NOT_FOCUSABLE
                hideKeyboard()
                registrationUseCase.enterSmsCode(stringSms)
                Toast.makeText(requireContext(), "Sms code passed", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.userId.observe(viewLifecycleOwner, Observer {
            if (it != "null"){
                userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
                val user = User(it.toString(), "", "", "", "", "", stringPhone)
                userViewModel.addUser(user)
                Toast.makeText(requireContext(), "Register ok", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        })
        /*register.observe(viewLifecycleOwner, Observer {
            if (it) {

            }
        })*/

        /*currentUserId.observe(viewLifecycleOwner, Observer {
            if (it != "null"){
                userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
                val user = User(it.toString(), "", "", "", "", "", stringPhone)
                userViewModel.addUser(user)
                Toast.makeText(requireContext(), "Register ok", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        })*/



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAlertDialog(string: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            binding.phoneNumber.focusable = View.NOT_FOCUSABLE
            /*registrationUseCase = RegistrationUseCase(
                firebaseRegistration = FirebaseRegistrationImpl()
            )*/
            registrationUseCase.enterPhoneNumber(phoneNumber = string)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Number is correct?")
        builder.setMessage(string)
        builder.create().show()
    }
}