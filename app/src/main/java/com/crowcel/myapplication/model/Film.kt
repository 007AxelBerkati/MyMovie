package com.crowcel.myapplication.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Film (
    var desc: String? = "",
    var director: String? = "",
    var genre: String? = "",
    var judul: String? = "",
    var poster: String? = "",
    var rating: String? = "",
    var tahun: String? = ""
)
: Parcelable