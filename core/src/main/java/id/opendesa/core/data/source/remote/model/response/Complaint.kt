package id.opendesa.core.data.source.remote.model.response


import com.google.gson.annotations.SerializedName

data class Complaint(
    @SerializedName("birth_date")
    val birthDate: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("device_uid")
    val deviceUid: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("nik")
    val nik: String = "",
    @SerializedName("report")
    val report: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)