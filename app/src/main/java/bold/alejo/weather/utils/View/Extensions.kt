package bold.alejo.weather.utils.View

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import bold.alejo.weather.core.presentation.SafeClickListener
import java.text.SimpleDateFormat

fun View.setSafeOnClickListener(onSafeClick: View.OnClickListener) {
    setOnClickListener(SafeClickListener(onSafeClick = onSafeClick))
}

fun View.show(doShow: Boolean = true) {
    visibility = if (doShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun String.getDateFormatted(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat("dd.MMM")
    return formatter.format(parser.parse(this))
}





