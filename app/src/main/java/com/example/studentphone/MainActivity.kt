package com.example.studentphone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var wakeWord: EditText
    private lateinit var nameEdit: EditText
    private lateinit var numberEdit: EditText
    private lateinit var contactsText: TextView

    private val permissionsLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            statusText.text = "स्टेटस: permissions check complete"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        wakeWord = findViewById(R.id.etWakeWord)
        nameEdit = findViewById(R.id.etName)
        numberEdit = findViewById(R.id.etNumber)
        contactsText = findViewById(R.id.tvContacts)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            requestPermissionsIfNeeded()
            startService(Intent(this, VoiceService::class.java))
            statusText.text = "स्टेटस: listening service चालू"
        }

        findViewById<Button>(R.id.btnStop).setOnClickListener {
            stopService(Intent(this, VoiceService::class.java))
            statusText.text = "स्टेटस: रुका हुआ"
        }

        findViewById<Button>(R.id.btnTest).setOnClickListener {
            Toast.makeText(this, "Voice test तैयार है", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnAccessibility).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val name = nameEdit.text.toString().trim()
            val number = numberEdit.text.toString().trim()

            if (name.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "नाम और नंबर डालें", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ContactsManager(this).saveContact(name, number)
            contactsText.text = ContactsManager(this).getContactsText()

            nameEdit.text.clear()
            numberEdit.text.clear()
        }
    }

    private fun requestPermissionsIfNeeded() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
        )

        val needed = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) !=
                    PackageManager.PERMISSION_GRANTED
        }

        if (needed.isNotEmpty()) {
            permissionsLauncher.launch(needed.toTypedArray())
        }
    }
}
