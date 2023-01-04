package id.opendesa.core.data.interactors

import id.opendesa.core.data.source.Resource
import id.opendesa.core.data.source.remote.model.request.ComplaintRequest
import id.opendesa.core.data.source.remote.model.response.Complaint
import id.opendesa.core.data.source.remote.network.SafeApiCall
import id.opendesa.core.domain.base.execute
import id.opendesa.core.domain.repository.ComplaintRepository
import id.opendesa.core.domain.usecase.ComplaintUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ComplaintInteractors(
  private val complaintRepository: ComplaintRepository
): ComplaintUseCase, SafeApiCall {
  override suspend fun getComplaintByUid(uid: String): Flow<Resource<List<Complaint>>> {
    return execute(Dispatchers.IO) {
      safeApiCall { complaintRepository.getComplaintByUid(uid) }
    }
  }

  override suspend fun postComplaint(complaintRequest: ComplaintRequest): Flow<Resource<Complaint>> {
    return execute(Dispatchers.IO) {
      safeApiCall { complaintRepository.postComplaint(complaintRequest) }
    }
  }

  override suspend fun removeComplaintById(id: String): Flow<Resource<Unit>> {
    return execute(Dispatchers.IO) {
      safeApiCall { complaintRepository.removeComplaintById(id) }
    }
  }
}