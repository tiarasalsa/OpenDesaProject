package id.opendesa.core.data.source.remote.network

import id.opendesa.core.data.source.remote.model.request.ComplaintRequest
import id.opendesa.core.data.source.remote.model.response.Complaint
import retrofit2.http.*

interface ApiService {

    @POST("v1/auth/login")
    suspend fun postComplaint(
        @Body complaintRequest: ComplaintRequest
    ): Complaint

    @GET("complaints/user/{uid}")
    suspend fun getComplaintByUid(
        @Path("uid") id: String
    ): List<Complaint>

    @DELETE("complaints/{id}")
    suspend fun removeComplaintById(
        @Path("id") id: String
    ): Unit

}