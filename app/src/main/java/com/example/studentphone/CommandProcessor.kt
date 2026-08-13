package com.example.studentphone

import android.content.Context
import android.content.Intent
import android.net.Uri

class CommandProcessor(
    private val context: Context
) {

    fun process(command: String): Boolean {
        val text = command.trim().lowercase()

        return when {
            text.contains("कॉल") && text.contains("काट") -> {
                false
            }

            text.startsWith("https://") ||
            text.startsWith("http://") -> {
                openUrl(text)
                true
            }

            else -> false
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
