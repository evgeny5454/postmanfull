package com.evgeny_m.postman.presentation.userscreens.settings.screens

import android.content.ContextWrapper
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.evgeny_m.data.models.User
import com.evgeny_m.data.viewmodels.UserViewModel
import com.evgeny_m.postman.databinding.FragmentPhotoEditorBinding
import com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet.PhotoBottomSheetAdapter.Companion.photoUri
import com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet.getPhotos
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModel
import com.evgeny_m.postman.presentation.viewModels.settings.SettingsViewModelFactory
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.util.*

const val EXTERNAL_STORAGE = "Images"

class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoEditorBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var viewModelSettings: SettingsViewModel
    private lateinit var userId: String
    private lateinit var userFirstName: String
    private lateinit var userLastName: String
    private lateinit var userName: String
    private lateinit var phoneNumber: String
    private lateinit var userBio: String
    private var storage = FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoEditorBinding.inflate(layoutInflater)


        viewModelSettings = ViewModelProvider(
            this,
            SettingsViewModelFactory(requireContext())
        )[SettingsViewModel::class.java]

        Glide
            .with(requireActivity())
            .load(photoUri)
            .into(binding.cropView)

         Log.d("ExternalStorage", "-> photoUri = $photoUri")

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            userId = it.last().id
            userName = it.last().userName
            phoneNumber = it.last().phoneNumber
            userFirstName = it.last().firstName
            userLastName = it.last().lastName
            userBio = it.last().bio
        })

        binding.buttonSave.setOnClickListener {
            val cropFile = binding.cropView.crop()
            val wrapper = ContextWrapper(requireContext())
            val folderToSave = wrapper.getExternalFilesDir(EXTERNAL_STORAGE)

            val uuid = UUID.randomUUID().toString()
            val file = File(folderToSave, "$uuid.jpg")
            val fOut = FileOutputStream(file)
            cropFile?.compress(Bitmap.CompressFormat.JPEG, 60, fOut)
            fOut.flush()
            fOut.close()

            MediaStore.Images.Media.insertImage(
                context?.contentResolver,
                cropFile,
                "PostmanImage",
                "jpg"
            )


            MediaScannerConnection.scanFile(requireContext(), arrayOf(file.toString()), null,
                OnScanCompletedListener { path, _ ->
                    Log.d("ExternalStorage", "Scanned $path:")
                    val items = getPhotos()
                    val item_uri = items.first().contentUri
                    Log.d("ExternalStorage", "-> uri = $item_uri")
                    viewModelSettings.editPhoto(item_uri)
                })


            val user = User(
                userId,
                userFirstName,
                userLastName,
                userName,
                userBio,
                file.absolutePath,
                phoneNumber
            )

            userViewModel.updateUser(user)
            findNavController().popBackStack()
        }


            return binding.root
        }

    }
