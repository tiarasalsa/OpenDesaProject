package id.opendesa.complaint.presentation.screen.detail

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import id.opendesa.complaint.R
import id.opendesa.complaint.presentation.component.button.LoadingButton
import id.opendesa.complaint.presentation.component.file_upload.FileUpload
import id.opendesa.complaint.presentation.component.pre.ErrorItem
import id.opendesa.complaint.presentation.component.pre.LoadingItem
import id.opendesa.complaint.presentation.component.toolbar.DefaultToolbar
import id.opendesa.complaint.presentation.theme.Blue
import id.opendesa.complaint.presentation.theme.DisableBackgroundField
import id.opendesa.complaint.presentation.theme.Header
import id.opendesa.core.data.source.remote.model.request.ComplaintRequest
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalPermissionsApi @ExperimentalComposeUiApi @SuppressLint("SimpleDateFormat")
@ExperimentalMaterial3Api @Composable fun DetailScreen(
  navController: NavHostController, viewModel: DetailViewModel = hiltViewModel()
) {
  val state by viewModel.detailState
  val categoryOptions =
    listOf("Infrastruktur (Sanitasi & Air)", "Pendidikan", "Kesehatan", "Anggaran Desa", "Lainnya")
  var expanded by rememberSaveable { mutableStateOf(false) }
  val calendarState = rememberSheetState()
  val selectedDates = rememberSaveable { mutableStateOf(Date()) }
  var cate by rememberSaveable { mutableStateOf(categoryOptions[0]) }
  var title by rememberSaveable { mutableStateOf("") }
  var report by rememberSaveable { mutableStateOf("") }
  var nik by rememberSaveable { mutableStateOf("") }
  var birthDate = SimpleDateFormat("yyyy-MM-dd").format(selectedDates.value)
  var images by rememberSaveable {
    mutableStateOf(arrayListOf("", "", "", ""))
  }
  val complaintRequest = ComplaintRequest(
    birthDate, cate, "", nik, report, title
  )
  var selectedFileUpload by rememberSaveable {
    mutableStateOf(0)
  }

  val readExternalStoragePermissionState = rememberPermissionState(
    Manifest.permission.READ_EXTERNAL_STORAGE
  )

  val imagePickerLauncher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
      uri?.let {
        images[selectedFileUpload] = it.toString()
      }
    }

  CalendarDialog(state = calendarState, config = CalendarConfig(
    yearSelection = true,
    monthSelection = true,
    style = CalendarStyle.MONTH,
  ), selection = CalendarSelection.Date { newDate ->
    selectedDates.value = Date(newDate.toEpochDay())
  })

  LaunchedEffect(state.isSuccess) {
    if (state.isSuccess) {
      navController.previousBackStackEntry?.savedStateHandle?.set("isPostSuccess", true)
      navController.popBackStack()
    }
  }

  Scaffold(modifier = Modifier
    .fillMaxSize()
    .background(Color.White), topBar = {
    DefaultToolbar(
      navController = navController,
      title = stringResource(id = R.string.write_complaint),
      backgroundColor = Blue,
      titleColor = Color.White
    )
  }) { contentPadding ->

    when {
      state.hasError -> {
        ErrorItem(
          message = state.errorMessage.orEmpty(),
          onButtonClick = { viewModel.postComplaint(complaintRequest) },
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
        )
      }
      else -> {
        Column(
          modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
          ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
              .padding(top = 16.dp)
          ) {
            OutlinedTextField(
              modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
              readOnly = true,
              value = cate,
              onValueChange = {},
              label = { Text(stringResource(id = R.string.category)) },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
              colors = ExposedDropdownMenuDefaults.textFieldColors(
                containerColor = DisableBackgroundField,
                focusedIndicatorColor = Blue,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Header
              )
            )
            ExposedDropdownMenu(
              expanded = expanded,
              onDismissRequest = { expanded = false },
              modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
            ) {
              categoryOptions.forEach { category ->
                DropdownMenuItem(modifier = Modifier.fillMaxWidth(),
                  text = { Text(text = category) },
                  onClick = {
                    cate = category
                    expanded = false
                  })
              }
            }
          }
          OutlinedTextField(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
              .padding(top = 8.dp),
            value = title,
            onValueChange = {
              title = it
            },
            maxLines = 1,
            label = { Text(stringResource(R.string.title)) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
              containerColor = DisableBackgroundField,
              focusedIndicatorColor = Blue,
              unfocusedIndicatorColor = Color.Transparent,
              textColor = Header
            )
          )
          OutlinedTextField(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
              .padding(top = 8.dp),
            value = report,
            onValueChange = { report = it },
            label = { Text(stringResource(R.string.report)) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
              containerColor = DisableBackgroundField,
              focusedIndicatorColor = Blue,
              unfocusedIndicatorColor = Color.Transparent,
              textColor = Header
            )
          )
          Text(
            text = stringResource(R.string.attachment),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
              .padding(top = 16.dp),
            color = Header,
            fontSize = 16.sp
          )
          LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
          ) {
            images.forEachIndexed { index, image ->
              item {
                FileUpload(
                  uri = if (image.isNotEmpty()) image.toUri() else null, onClick = {
                    selectedFileUpload = index
                    if (readExternalStoragePermissionState.status != PermissionStatus.Granted) {
                      readExternalStoragePermissionState.launchPermissionRequest()
                    } else {
                      imagePickerLauncher.launch("image/*")
                    }
                  }, modifier = Modifier.size(150.dp)
                )
              }
            }
          }

          Text(
            text = stringResource(R.string.reporter_verification),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
              .padding(top = 32.dp),
            color = Header,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
          )
          OutlinedTextField(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
              .padding(top = 8.dp),
            value = nik,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { nik = it },
            label = { Text(stringResource(R.string.nik)) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
              containerColor = DisableBackgroundField,
              focusedIndicatorColor = Blue,
              unfocusedIndicatorColor = Color.Transparent,
              textColor = Header
            )
          )
          OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .onFocusChanged {
              if (it.isFocused) {
                calendarState.show()
              }
            },
            value = birthDate,
            maxLines = 1,
            onValueChange = { birthDate = it },
            label = { Text(stringResource(R.string.birth_date)) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
              containerColor = DisableBackgroundField,
              focusedIndicatorColor = Blue,
              unfocusedIndicatorColor = Color.Transparent,
              textColor = Header
            )
          )
          LoadingButton(
            text = stringResource(id = R.string.submit),
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
              .padding(top = 30.dp)
              .padding(bottom = 16.dp)
              .height(50.dp),
            onClick = { viewModel.postComplaint(complaintRequest) },
            enabled = true,
            loading = state.isLoading
          )
        }
      }
    }
  }
}