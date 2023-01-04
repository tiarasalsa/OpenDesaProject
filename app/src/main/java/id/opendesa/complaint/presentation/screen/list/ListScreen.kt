package id.opendesa.complaint.presentation.screen.list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import id.opendesa.complaint.R
import id.opendesa.complaint.presentation.component.pre.ErrorItem
import id.opendesa.complaint.presentation.component.pre.LoadingItem
import id.opendesa.complaint.presentation.component.toolbar.DefaultToolbar
import id.opendesa.complaint.presentation.theme.Blue
import id.opendesa.complaint.presentation.theme.Orange
import id.opendesa.complaint.utils.Router
import id.opendesa.core.data.source.remote.model.response.Complaint

@ExperimentalMaterialApi @ExperimentalMaterial3Api @Composable fun ListScreen(
  navController: NavHostController, viewModel: ListViewModel = hiltViewModel()
) {
  val state: ListScreenState by viewModel.listState
  val listState = rememberLazyListState()
  // The FAB is initially expanded. Once the first visible item is past the first item we
  // collapse the FAB. We use a remembered derived state to minimize unnecessary compositions.
  val expandedFab by remember {
    derivedStateOf {
      listState.firstVisibleItemIndex == 0
    }
  }

  navController.GetOnceResult<Boolean>(keyResult = "isPostSuccess") {
    viewModel.getComplaints()
  }

  Scaffold(modifier = Modifier
    .fillMaxSize()
    .background(Color.White), topBar = {
    DefaultToolbar(
      navController = navController,
      title = stringResource(id = R.string.sikema),
      backgroundColor = Blue,
      titleColor = Color.White
    )
  }, floatingActionButton = {
    ExtendedFloatingActionButton(
      onClick = { navController.navigate(Router.DETAIL) },
      expanded = expandedFab,
      icon = { Icon(
        painter = painterResource(id = R.drawable.paper_plane),
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(24.dp)
      ) },
      text = { Text(text = stringResource(R.string.send_complaint), color = Color.White) },
      containerColor = Orange
    )
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
          LazyColumn(
            state = listState,
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(state.data!!.reversed()) { complaint ->
              ComplaintItem(complaint = complaint, onSwipeDelete = { complaint ->
                viewModel.removeComplaint(complaint.id.toString())
              }, onSwipeEdit = { complaint ->

              })
            }
          }
        }
      }
    }
  }
}

@Composable fun <T> NavController.GetOnceResult(keyResult: String, onResult: (T) -> Unit) {
  val valueScreenResult =
    currentBackStackEntry?.savedStateHandle?.getStateFlow<T?>(keyResult, null)?.collectAsState()

  valueScreenResult?.value?.let {
    onResult(it)

    currentBackStackEntry?.savedStateHandle?.remove<T>(keyResult)
  }
}