package id.opendesa.complaint.presentation.screen.list

import id.opendesa.core.data.source.remote.model.response.Complaint

data class ListScreenState(
  val data: List<Complaint>? = null,
  val isLoading: Boolean = false,
  val hasError: Boolean = false,
  val errorMessage: String? = null
)