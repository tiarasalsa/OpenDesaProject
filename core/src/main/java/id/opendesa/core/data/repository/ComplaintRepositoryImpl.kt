package id.opendesa.core.data.repository

import id.opendesa.core.data.source.remote.model.request.ComplaintRequest
import id.opendesa.core.data.source.remote.model.response.Complaint
import id.opendesa.core.data.source.remote.network.ApiService
import id.opendesa.core.domain.repository.ComplaintRepository

class ComplaintRepositoryImpl(private val apiService: ApiService): ComplaintRepository {
  override suspend fun getComplaintByUid(uid: String): List<Complaint> {
    return apiService.getComplaintByUid(uid)
  }

  override suspend fun postComplaint(complaintRequest: ComplaintRequest): Complaint {
    return apiService.postComplaint(complaintRequest)
  }

  override suspend fun removeComplaintById(id: String) {
    return apiService.removeComplaintById(id)
  }

}