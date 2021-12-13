package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.evgeny_m.data.models.User
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentChangeUserFullNameBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.hideKeyboard


class ChangeUserFullNameFragment : Fragment() {

    private lateinit var binding: FragmentChangeUserFullNameBinding
    //private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChangeUserFullNameBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            binding.editFirstName.setText(it.last().firstName)
            binding.editLastName.setText(it.last().lastName)
        })
      /*  viewModel.userFirstName.observe(viewLifecycleOwner, Observer {
            binding.editFirstName.setText(it)
        })
        viewModel.userLastName.observe(viewLifecycleOwner, Observer {
            binding.editLastName.setText(it)
        })*/

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    val firstName = binding.editFirstName.text.toString()
                    val lastName = binding.editLastName.text.toString()
                    val user = User("73723rh2r",firstName,lastName,"","","","" )
                    //viewModel.changeFullName(userFirstName = firstName, userLastName = lastName)
                    userViewModel.addUser(user)
                    hideKeyboard()
                    view?.findNavController()?.popBackStack()
                    true
                }
                else -> false
            }
        }
        return binding.root
    }
}