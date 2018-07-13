package com.example.abdellahberghachi.facephoto.utils.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Paging {

    @SerializedName("cursors")
    @Expose
    var cursors: Cursors? = null

    override fun toString(): String {
        return "Paging{" +
                "cursors=" + cursors +
                '}'.toString()
    }
}
