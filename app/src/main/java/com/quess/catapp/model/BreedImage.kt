package com.quess.catapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BreedImage(
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @SerializedName("breeds")
    @Expose
    var breeds: List<Breed?>? = null
) : Parcelable