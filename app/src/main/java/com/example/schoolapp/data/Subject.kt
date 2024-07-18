package com.example.schoolapp.data

import com.google.gson.annotations.SerializedName

data class Subject(
    @SerializedName("name") val name: String = "",
    @SerializedName("shortName") val shortName: String = "",
    @SerializedName("color") val color: String = "",
) {
}
