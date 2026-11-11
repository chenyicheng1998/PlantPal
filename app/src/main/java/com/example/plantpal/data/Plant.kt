package com.example.plantpal.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

/** How much light a plant needs. Room stores enums as text automatically. */
enum class LightNeed { LOW, MEDIUM, BRIGHT }

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val wateringIntervalDays: Int,
    val lightNeed: LightNeed,
    val createdDate: LocalDate = LocalDate.now()
)