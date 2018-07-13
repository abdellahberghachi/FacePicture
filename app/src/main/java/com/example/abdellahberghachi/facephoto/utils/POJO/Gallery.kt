package com.example.abdellahberghachi.facephoto.utils.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Gallery {
    @SerializedName("images")
    @Expose
    var images: List<Image>? = null
    @SerializedName("id")
    @Expose
    var id: String? = null

    override fun toString(): String {
        return "Gallery(images=$images, id=$id)"
    }


}
