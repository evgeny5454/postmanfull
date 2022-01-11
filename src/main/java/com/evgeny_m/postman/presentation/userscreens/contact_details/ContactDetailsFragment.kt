package com.evgeny_m.postman.presentation.userscreens.contact_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.evgeny_m.postman.databinding.FragmentContactDetailsBinding
import com.evgeny_m.postman.presentation.userscreens.settings.utils.initBackButton


class ContactDetailsFragment : Fragment() {

    private val args by navArgs<ContactDetailsFragmentArgs>()
    private lateinit var binding: FragmentContactDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(layoutInflater)
        initBackButton(binding.toolbar)
        binding.toolbar.title = args.currentUser.name
        binding.accountSettings.userPhone.text = args.currentUser.phone
        binding.accountSettings.userName.text = "@${args.currentUser.userName}"
        binding.accountSettings.userBio.text = args.currentUser.bio

        Glide.with(requireActivity())
            .load(args.currentUser.photo)
            .thumbnail(0.33f)
            .centerCrop()
            .into(binding.userPhoto)

        return binding.root
    }
}