package com.example.abdellahberghachi.facephoto.utils.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GalleryPojo {
    @SerializedName("data")
    @Expose
    var data: List<Gallery>? = null
    @SerializedName("paging")
    @Expose
     var paging: Paging? = null


}