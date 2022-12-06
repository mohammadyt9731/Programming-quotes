package com.example.programmingquotes.core.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.programmingquotes.R

fun Context.openUri(uri: String) {
    try {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(this, "Invalid uri", Toast.LENGTH_SHORT).show()
    }
}

fun Context.shareText(text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(
        Intent.createChooser(
            intent,
            getString(R.string.label_share_with)
        )
    )
}