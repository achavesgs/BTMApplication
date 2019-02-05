package br.com.btm.btmapplication

import com.google.gson.annotations.SerializedName

data class Usuario(
        @SerializedName("email") val email: String,
        @SerializedName("uid") val userId: String
)