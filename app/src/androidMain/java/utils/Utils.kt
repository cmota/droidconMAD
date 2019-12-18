package utils

import java.text.SimpleDateFormat
import java.util.*

private const val DEFAULT_NO_DATE = "-"

object Utils {

    private val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    fun getFormattedDate(date: String?): String {
        if (date == null) {
            return DEFAULT_NO_DATE
        }
        return formatter.format(parser.parse(date))
    }
}