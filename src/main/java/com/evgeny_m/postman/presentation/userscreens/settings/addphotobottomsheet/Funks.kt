package com.evgeny_m.postman.presentation.userscreens.settings.addphotobottomsheet

import android.annotation.SuppressLint
import android.content.ContentUris
import android.provider.MediaStore
import com.evgeny_m.postman.presentation.models.MediaStoreImage
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit



fun PhotoBottomSheetFragment.getPhotos() : List<MediaStoreImage> {

    var arrayPhotos = mutableListOf<MediaStoreImage>()

    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.DATE_ADDED
    )

    val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"

    val selectionArgs = arrayOf(
        // Release day of the G1. :)
        dateToTimestamp(day = 22, month = 10, year = 2008).toString()
    )

    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    activity?.contentResolver?.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )?.use { cursor ->

        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val dateModifiedColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
        val displayNameColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val dateModified =
                Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
            val displayName = cursor.getString(displayNameColumn)
            val contentUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )
            val image = MediaStoreImage(id, displayName, dateModified, contentUri)
            arrayPhotos.add(image)

        }
        cursor.close()
    }
    return arrayPhotos
}

@SuppressLint("SimpleDateFormat")
private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
    SimpleDateFormat("dd.MM.yyyy").let { formatter ->
        TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
    }