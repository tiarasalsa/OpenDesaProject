package id.opendesa.complaint.presentation.screen.list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import id.opendesa.complaint.R
import id.opendesa.complaint.presentation.component.pre.ErrorItem
import id.opendesa.complaint.presentation.component.pre.LoadingItem
import id.opendesa.complaint.presentation.component.toolbar.DefaultToolbar
import id.opendesa.complaint.presentation.theme.Blue

@ExperimentalMaterialApi @ExperimentalMaterial3Api @Composable fun ListScreen(
  navController: NavHostController, viewModel: ListViewModel = hiltViewModel()
) {
  val state: ListScreenState by viewModel.listState
  val context = LocalContext.current

  Scaffold(modifier = Modifier
    .fillMaxSize()
    .background(Color.White), topBar = {
    DefaultToolbar(navController = navController, title = stringResource(id = R.string.complaint))
  }, floatingActionButton = {
    FloatingActionButton(onClick = { /*TODO*/ }, backgroundColor = Blue) {
      Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.White)
    }
  }) { contentPadding ->
    when {
      state.isLoading -> {
        LoadingItem(
          modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
        )
      }
      state.hasError -> {
        ErrorItem(
          message = state.errorMessage.orEmpty(),
          onButtonClick = { viewModel.getComplaints() },
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
        )
      }
      else -> {
        if (state.data.isNullOrEmpty()) {
          ErrorItem(
            imageRes = R.drawable.not_found,
            title = stringResource(R.string.empty_data),
            message = stringResource(R.string.empty_desc),
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp)
          )
        } else {
          LazyColumn(modifier = Modifier.padding(contentPadding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.data!!) { complaint ->
              ComplaintItem(
                complaint = complaint,
                onSwipeDelete = { complaint ->
                  viewModel.removeComplaint(complaint.id.toString())
                },
                onSwipeEdit = { complaint ->
                  Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show()
                }
              )
            }
          }
        }
      }
    }
  }
}