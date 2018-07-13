package com.example.abdellahberghachi.facephoto.utils.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cursors {

    @SerializedName("before")
    @Expose
    var before: String? = null
    @SerializedName("after")
    @Expose
    var after: String? = null
}
