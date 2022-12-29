package id.opendesa.core.domain.usecase

import id.opendesa.core.data.source.Resource
import id.opendesa.core.data.source.remote.model.request.ComplaintRequest
import id.opendesa.core.data.source.remote.model.response.Complaint
import kotlinx.coroutines.flow.Flow

interface ComplaintUseCase {
  suspend fun getComplaintByUid(uid: String): Flow<Resource<List<Complaint>>>
  suspend fun postComplaint(complaintRequest: ComplaintRequest): Flow<Resource<Complaint>>
  suspend fun removeComplaintById(id: String): Flow<Resource<Unit>>
}