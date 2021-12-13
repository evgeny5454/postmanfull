package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.evgeny_m.postman.databinding.FragmentPhotoBinding
import com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet.PhotoBottomSheetAdapter.Companion.photoUri
import com.evgeny_m.postman.presentation.userscreens.settings.utils.saveImageToExternalStorage

const val EXTERNAL_STORAGE = "Images"

class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoBinding

    //private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(layoutInflater)

        Glide
            .with(requireActivity())
            .load(photoUri)
            .into(binding.cropView)

        binding.buttonSave.setOnClickListener {
            val cropFile = binding.cropView.crop()
            val wrapper = ContextWrapper(requireContext())
            val folderToSave = wrapper.getExternalFilesDir(EXTERNAL_STORAGE)
            val profileImageUri = saveImageToExternalStorage(folderToSave,cropFile)
            //viewModel.setPhotoUri(profileImageUri)
            view?.findNavController()?.popBackStack()
        }

        return binding.root
    }
}