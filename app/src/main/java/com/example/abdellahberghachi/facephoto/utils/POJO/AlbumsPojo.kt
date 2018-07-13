package com.example.abdellahberghachi.facephoto.utils.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AlbumsPojo {
    @SerializedName("data")
    @Expose
    var data: List<Albums>? = null
    @SerializedName("paging")
    @Expose
    var paging: Paging? = null

    override fun toString(): String {
        return "AlbumsPojo{" +
                "data=" + data +
                ", paging=" + paging +
                '}'.toString()
    }
}
