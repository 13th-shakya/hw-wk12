package com.example.kotlinhomework4

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BroadcastService : Service() {
    private var channel = ""
    private lateinit var thread: Thread

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }

        sendBroadcast(
            when (channel) {
                "music" -> "歡迎來到音樂頻道"
                "news" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                else -> "頻道錯誤"
            }
        )

        if (::thread.isInitialized && thread.isAlive)
            thread.interrupt()

        thread = Thread {
            try {
                Thread.sleep(3000) // 延遲三秒
                sendBroadcast(
                    when (channel) {
                        "music" -> "即將播放本月TOP10音樂"
                        "news" -> "即將為您提供獨家新聞"
                        "sport" -> "即將播報本週NBA賽事"
                        else -> "頻道錯誤"
                    }
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        thread.start()
        return START_STICKY
    }

    private fun sendBroadcast(message: String) =
        sendBroadcast(Intent(channel).putExtra("message", message))

    override fun onBind(intent: Intent): IBinder? = null
}
