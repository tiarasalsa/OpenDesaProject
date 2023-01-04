package id.opendesa.core.data.source.remote.model.request


import com.google.gson.annotations.SerializedName

data class ComplaintRequest(
    @SerializedName("birth_date")
    val birthDate: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("device_uid")
    val deviceUid: String = "",
    @SerializedName("nik")
    val nik: String = "",
    @SerializedName("report")
    val report: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("images")
    val imagesUri: List<String> = arrayListOf()
)