package br.com.btm.btmapplication

import com.google.gson.annotations.SerializedName

data class UsuarioResponse(
        @SerializedName("user") val usuario: Usuario
)