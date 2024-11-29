package com.example.kotlinhomework4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinhomework4.databinding.ActivityBroadcastReceiverBinding

class BroadcastReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadcastReceiverBinding

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.extras?.let {
                binding.tvBroadcastReceiver.text = it.getString("message")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadcastReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMusicChannel.setOnClickListener {
            register("music")
        }

        binding.btnNewsChannel.setOnClickListener {
            register("news")
        }

        binding.btnSportChannel.setOnClickListener {
            register("sport")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun register(channel: String) {
        registerReceiver(receiver, IntentFilter(channel), RECEIVER_EXPORTED)
        val intent = Intent(this, BroadcastService::class.java)
            .putExtra("channel", channel)
        startService(intent)
        Toast.makeText(this, "Starting service", Toast.LENGTH_SHORT).show()
    }
}
