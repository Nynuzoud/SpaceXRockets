package com.example.sergeykuchin.spacexrockets.ui.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket")
data class Rocket(

    @PrimaryKey
    val rocketId: String,
    val id: Long,
    val rocketName: String = "",
    val country: String = "",
    val enginesNumber: Int,
    val active: Boolean,
    val imageUrl: String,
    val description: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rocket

        if (rocketId != other.rocketId) return false
        if (id != other.id) return false
        if (rocketName != other.rocketName) return false
        if (country != other.country) return false
        if (enginesNumber != other.enginesNumber) return false
        if (active != other.active) return false
        if (imageUrl != other.imageUrl) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rocketId.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + rocketName.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + enginesNumber
        result = 31 * result + active.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}