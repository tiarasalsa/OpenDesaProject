package id.opendesa.complaint.presentation.component.toolbar

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import id.opendesa.complaint.presentation.theme.Header
import id.opendesa.complaint.R
import id.opendesa.complaint.presentation.theme.Blue

@ExperimentalMaterial3Api
@Composable
fun DefaultToolbar(
    title: String = "",
    navController: NavHostController,
    hideBackArrow: Boolean = false,
    shareSubject: String = "",
    shareUrl: String = "",
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Header
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Header
            )
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null && !hideBackArrow) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = Blue
                    )
                }
            } else {
                null
            }
        },
        actions = {
            if(shareUrl.isNotEmpty()) {
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl)

                    ContextCompat.startActivity(
                        context,
                        Intent.createChooser(intent, "Share With"),
                        null
                    )
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = stringResource(R.string.share),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    )
}