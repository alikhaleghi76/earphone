package ali.khaleghi.earphone.ui.activity

import ali.khaleghi.earphone.const.Const
import ali.khaleghi.earphone.const.Const.Companion.sharedPreferencesName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.system.exitProcess
import android.view.KeyEvent
import android.os.SystemClock
import android.media.AudioManager


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        handleAction()

        finish()
        exitProcess(0)
    }

    private fun handleAction() {
        val prefs = getSharedPreferences(
            sharedPreferencesName, MODE_PRIVATE
        )
        when (prefs.getInt(Const.selectedActionKey, 0)) {
            0 -> skipPreviousTrack(this.applicationContext)
            1 -> skipTrack(this.applicationContext)
            2 -> previousTrack(this.applicationContext)
            3 -> volumeUpDown(this.applicationContext)
            4 -> volumeUp(this.applicationContext)
            5 -> volumeDown(this.applicationContext)

            else -> skipTrack(this.applicationContext)
        }

    }

    private fun skipTrack(appContext: Context) {
        val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val eventTime = SystemClock.uptimeMillis()
        val keyCode = KeyEvent.KEYCODE_MEDIA_NEXT
        pressKey(keyCode, audioManager, eventTime)
    }

    private fun previousTrack(appContext: Context) {
        val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val eventTime = SystemClock.uptimeMillis()
        val keyCode = KeyEvent.KEYCODE_MEDIA_PREVIOUS
        pressKey(keyCode, audioManager, eventTime)
    }

    private fun skipPreviousTrack(appContext: Context) {
        val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val eventTime = SystemClock.uptimeMillis()
        var keyCode = KeyEvent.KEYCODE_MEDIA_NEXT

        if (!audioManager.isMusicActive) {
            keyCode = KeyEvent.KEYCODE_MEDIA_PREVIOUS
        }
        pressKey(keyCode, audioManager, eventTime)
    }

    private fun volumeUp(appContext: Context) {
        val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
    }

    private fun volumeDown(appContext: Context) {
        val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
        audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
    }

    private fun volumeUpDown(appContext: Context) {
        val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioManager.isMusicActive) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        } else {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
        }
    }

    private fun pressKey(keyCode: Int, audioManager: AudioManager, eventTime: Long) {
        val downEvent = KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0)
        audioManager.dispatchMediaKeyEvent(downEvent)

        val upEvent = KeyEvent(eventTime, eventTime, KeyEvent.ACTION_UP, keyCode, 0)
        audioManager.dispatchMediaKeyEvent(upEvent)
    }

}