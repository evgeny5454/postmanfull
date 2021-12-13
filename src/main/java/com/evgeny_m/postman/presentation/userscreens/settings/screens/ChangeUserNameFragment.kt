package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentChangeUserNameBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.hideKeyboard

class ChangeUserNameFragment : Fragment() {

    private lateinit var binding: FragmentChangeUserNameBinding
    //private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeUserNameBinding.inflate(layoutInflater)

       /* viewModel.userName.observe(viewLifecycleOwner, Observer {
            binding.editText.setText(it)
        })*/

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.save -> {
                    val text = binding.editText.text.toString()
                    //viewModel.changeUserName(text)
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