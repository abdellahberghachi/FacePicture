package com.example.abdellahberghachi.facephoto.utils.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Albums {

    @SerializedName("created_time")
    @Expose
    var createdTime: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null


    override fun toString(): String {
        return "Albums{" +
                "createdTime='" + createdTime + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", id='" + id + '\''.toString() +
                '}'.toString()
    }
}
