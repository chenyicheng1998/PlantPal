package com.example.plantpal.domain

/** Pure Kotlin input checks. Each function returns an error message, or null if the input is valid. */
object PlantValidator {
    const val MAX_NAME_LENGTH = 40
    const val MIN_INTERVAL_DAYS = 1
    const val MAX_INTERVAL_DAYS = 365

    fun validateName(name: String): String? = when {
        name.isBlank() -> "Please enter a plant name."
        name.trim().length > MAX_NAME_LENGTH -> "The name can have at most $MAX_NAME_LENGTH characters."
        else -> null
    }

    fun validateInterval(text: String): String? {
        val days = text.trim().toIntOrNull()
            ?: return "Enter the number of days as a whole number."
        return if (days in MIN_INTERVAL_DAYS..MAX_INTERVAL_DAYS) {
            null
        } else {
            "Choose between $MIN_INTERVAL_DAYS and $MAX_INTERVAL_DAYS days."
        }
    }
}