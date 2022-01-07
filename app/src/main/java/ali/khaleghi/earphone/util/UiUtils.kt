package ali.khaleghi.earphone.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )


fun vibrate(context: Context, durationInMillis: Long = 30) {
    val v = context.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(VibrationEffect.createOneShot(durationInMillis, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        v.vibrate(durationInMillis)
    }
}