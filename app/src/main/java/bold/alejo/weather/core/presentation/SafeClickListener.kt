package bold.alejo.weather.core.presentation

import android.os.SystemClock
import android.view.View

class SafeClickListener(
    private var defaultInterval: Long = 1000L,
    private val onSafeClick: View.OnClickListener
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeClick.onClick(v)
    }
}
