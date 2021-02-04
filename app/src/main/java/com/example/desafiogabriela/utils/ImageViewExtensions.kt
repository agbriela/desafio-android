package com.example.desafiogabriela.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImageURL(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
}
