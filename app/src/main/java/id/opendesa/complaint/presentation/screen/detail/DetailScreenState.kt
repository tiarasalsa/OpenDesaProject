package id.opendesa.complaint.presentation.screen.detail

import id.opendesa.core.data.source.remote.model.response.Complaint

data class DetailScreenState(
  val data: Complaint? = null,
  val isSuccess: Boolean = false,
  val isLoading: Boolean = false,
  val hasError: Boolean = false,
  val errorMessage: String? = null
)