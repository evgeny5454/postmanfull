package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.evgeny_m.postman.databinding.FragmentNavigationBinding


class NavigationHeaderFragment : Fragment() {

    lateinit var binding: FragmentNavigationBinding
    //private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavigationBinding.inflate(layoutInflater)

       /*viewModel.userFirstName.observe(viewLifecycleOwner,{
            binding.navUserName.text = it
        })*/
        /*viewModel.photoUri.observe(viewLifecycleOwner,{
            Glide.with(requireContext())
                .load(it)
                .thumbnail(0.33f)
                .centerCrop()
                .into(binding.userPhotoFelid)
        })*/
        binding.navDrawerPhotoFiend.setOnClickListener {
            //viewModel.loadUserData()
        }
        return binding.root
    }
}