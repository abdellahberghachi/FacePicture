package com.example.abdellahberghachi.facephoto.utils.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image {
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("source")
    @Expose
    var source: String? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null

    override fun toString(): String {
        return "Image(height=$height, source=$source, width=$width)"
    }

}
