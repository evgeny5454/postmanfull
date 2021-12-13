package com.evgeny_m.postman.presentation.userscreens.settings.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.evgeny_m.postman.R
import com.evgeny_m.postman.drawerLayout
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun saveImageToExternalStorage(folderToSave: File?, cropFile: Bitmap?) : String {
    val uuid = UUID.randomUUID().toString()
    val file = File(folderToSave, "$uuid.jpg")
    val fOut = FileOutputStream(file)
    cropFile?.compress(Bitmap.CompressFormat.JPEG, 60, fOut)
    fOut.flush()
    fOut.close()
    return file.absolutePath
}

fun Fragment.initBackButton(toolbar: Toolbar) {
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    toolbar.setNavigationIcon(R.drawable.ic_back)
    toolbar.setNavigationOnClickListener {
        view?.findNavController()?.popBackStack()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}

fun Fragment.initNavButton(toolbar: Toolbar) {
    toolbar.setNavigationIcon(R.drawable.ic_main_navigation)
    toolbar.setNavigationOnClickListener {
        drawerLayout.open()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}