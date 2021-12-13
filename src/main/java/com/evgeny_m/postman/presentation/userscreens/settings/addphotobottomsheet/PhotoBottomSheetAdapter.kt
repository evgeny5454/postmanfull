package com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evgeny_m.postman.R
import com.evgeny_m.postman.databinding.ItemPhotoBinding
import com.evgeny_m.postman.presentation.models.MediaStoreImage
import com.evgeny_m.postman.presentation.userscreens.settings.screens.photoBottomSheetFragment

class PhotoBottomSheetAdapter(
    private val context: Context,
    private val activity: FragmentActivity?,
    //private val viewModel: SettingsViewModel
) :
    RecyclerView.Adapter<PhotoBottomSheetAdapter.PhotoFragmentHolder>() {

    private var listImagesCache = emptyList<MediaStoreImage>()

    class PhotoFragmentHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPhotoBinding.bind(view)
        val image = binding.itemImage
        val imageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoFragmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoFragmentHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoFragmentHolder, position: Int) {

        Glide.with(context)
            .load(listImagesCache[position].contentUri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.image)
        holder.imageView.setOnClickListener {
            photoUri = listImagesCache[position].contentUri
            //viewModel.setPhotoUri(photoUri.toString())
            photoBottomSheetFragment.dismiss()
            activity?.findNavController(R.id.nav_content_host)?.navigate(R.id.photoFragment)

        }

    }

    override fun getItemCount(): Int {
        return listImagesCache.size
    }

    fun addImages(list: List<MediaStoreImage>) {
        listImagesCache = list
        notifyDataSetChanged()
    }

    companion object {
        lateinit var photoUri: Uri
    }
}