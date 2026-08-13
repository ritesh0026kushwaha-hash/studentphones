package com.example.studentphone

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class ContactsManager(
    private val context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "student_phone_contacts",
            Context.MODE_PRIVATE
        )

    fun saveContact(name: String, number: String) {
        val contacts = getContacts()

        contacts.put(
            JSONObject()
                .put("name", name)
                .put("number", number)
        )

        prefs.edit()
            .putString("contacts", contacts.toString())
            .apply()
    }

    fun getContacts(): JSONArray {
        val raw = prefs.getString("contacts", null)

        return if (raw.isNullOrEmpty()) {
            JSONArray()
        } else {
            try {
                JSONArray(raw)
            } catch (_: Exception) {
                JSONArray()
            }
        }
    }

    fun getContactsText(): String {
        val contacts = getContacts()

        if (contacts.length() == 0) {
            return "अभी कोई कॉन्टैक्ट नहीं है।"
        }

        val result = StringBuilder()

        for (i in 0 until contacts.length()) {
            val item = contacts.getJSONObject(i)

            result.append(
                "${item.optString("name")} : " +
                "${item.optString("number")}\n"
            )
        }

        return result.toString()
    }
}
