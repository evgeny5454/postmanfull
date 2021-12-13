package com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.evgeny_m.postman.databinding.FragmentPhotoBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PhotoBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPhotoBottomSheetBinding
    //private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBottomSheetBinding.inflate(layoutInflater)
        val activity = activity
        val listPhotos = getPhotos()
        val adapter = PhotoBottomSheetAdapter(requireContext(), activity)
        adapter.addImages(listPhotos)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return binding.root

    }

    companion object {
        const val TAG = "PhotoBottomSheetFragment"
    }
}
