package id.opendesa.complaint.utils.ext

import android.annotation.SuppressLint
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.github.marlonlom.utilities.timeago.TimeAgoMessages
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toDateString(): String {
  val dateFormat = SimpleDateFormat("yyyy-MM-dd")
  val date = this.time
  return dateFormat.format(date)
}

fun Date.toTimeAgo(): String {
  val locale = Locale.forLanguageTag("id")
  val messages: TimeAgoMessages = TimeAgoMessages.Builder().withLocale(locale).build()

  return TimeAgo.using(this.time, messages)
}