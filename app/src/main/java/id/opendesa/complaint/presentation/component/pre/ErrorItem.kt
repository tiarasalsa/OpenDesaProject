package id.opendesa.complaint.presentation.component.pre

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.opendesa.complaint.R

@Composable fun ErrorItem(
  message: String,
  @DrawableRes imageRes: Int = R.drawable.error,
  title: String = stringResource(R.string.something_error_title),
  textButton: String = stringResource(R.string.try_again),
  onButtonClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier.fillMaxSize()
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(painter = painterResource(id = imageRes), contentDescription = null)
    Spacer(modifier = Modifier.height(10.dp))
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally),
      text = title,
      style = MaterialTheme.typography.titleMedium.copy(
        textAlign = TextAlign.Center
      )
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = message,
      style = MaterialTheme.typography.bodyMedium.copy(
        textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface
      )
    )
    Spacer(modifier = Modifier.height(16.dp))
    onButtonClick?.let {
      Button(onClick = { it.invoke() }) {
        Text(text = textButton, color = MaterialTheme.colorScheme.surface)
      }
    }
  }
}

@Preview(showBackground = true) @Composable fun ErrorScreenPreview() {
  MaterialTheme() {
    ErrorItem("Test", onButtonClick = { /*TODO*/ })
  }
}