package id.opendesa.complaint.presentation.component.file_upload

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import id.opendesa.complaint.R
import id.opendesa.complaint.presentation.theme.Blue
import id.opendesa.complaint.presentation.theme.Border

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun FileUpload(
  uri: Uri? = null,
  onClick: () -> Unit,
  modifier: Modifier,
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(15.dp),
    border = BorderStroke(
      width = 1.dp,
      color = Border
    ),
    onClick = onClick
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
      contentAlignment = Alignment.Center
    ) {
      if (uri == null) {
        Column(
          modifier = Modifier
            .fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Image(
            modifier = Modifier.padding(20.dp, 28.dp, 20.dp, 0.dp),
            painter = painterResource(id = R.drawable.ic_cloud_upload),
            contentDescription = null
          )
          Text(
            modifier = Modifier.padding(20.dp, 5.dp, 20.dp, 28.dp),
            text = stringResource(R.string.pick_here),
            style = MaterialTheme.typography.bodySmall,
            color = Blue
          )
        }
      } else {
        AsyncImage(
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop,
          model = uri,
          contentDescription = null
        )
      }
    }
  }
}