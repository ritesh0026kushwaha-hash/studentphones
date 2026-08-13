package com.example.studentphone

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class CallEndAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(
        event: AccessibilityEvent?
    ) {
        // Accessibility service is intentionally passive.
        // It does not monitor or transmit screen content.
    }

    override fun onInterrupt() {
        // Service interrupted.
    }
}
