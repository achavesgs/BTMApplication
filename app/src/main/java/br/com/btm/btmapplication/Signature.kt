package br.com.btm.btmapplication

import com.google.gson.annotations.SerializedName
import java.util.*

data class Signature(
        @SerializedName("data") val data: Date,
        @SerializedName("servico") val servico: String,
        @SerializedName("tempo") val tempo: Boolean
)