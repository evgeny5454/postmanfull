package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.FragmentNavigationBinding


class NavigationHeaderFragment : Fragment() {

    lateinit var binding: FragmentNavigationBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavigationBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                if(it.last().firstName == "null") binding.userName.text = ""
                else binding.userName.text = it.last().firstName
                if(it.last().phoneNumber == "null") binding.userPhone.text = ""
                else binding.userPhone.text = it.last().phoneNumber

                Glide.with(requireContext())
                    .load(it.last().photoUrl)
                    .centerCrop()
                    .into(binding.userPhoto)
            }
        })

        binding.materialUserPhoto.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        return binding.root
    }
}