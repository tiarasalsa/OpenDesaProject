package id.opendesa.core.domain.repository

import id.opendesa.core.data.source.remote.model.request.ComplaintRequest
import id.opendesa.core.data.source.remote.model.response.Complaint

interface ComplaintRepository {
  suspend fun getComplaintByUid(uid: String): List<Complaint>
  suspend fun postComplaint(complaintRequest: ComplaintRequest): Complaint
  suspend fun removeComplaintById(id: String)
}