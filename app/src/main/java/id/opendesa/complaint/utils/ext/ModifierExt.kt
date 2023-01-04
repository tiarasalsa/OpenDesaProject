package id.opendesa.complaint.utils.ext

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.animateScale(): Modifier = composed {
  var selected by remember { mutableStateOf(false) }
  val scale by animateFloatAsState(if (selected) 0.9f else 1f)

  scale(scale)
    .pointerInput(selected) {
      awaitPointerEventScope {
        selected = if (selected) {
          waitForUpOrCancellation()
          false
        } else {
          awaitFirstDown(false)
          true
        }
      }
    }
}