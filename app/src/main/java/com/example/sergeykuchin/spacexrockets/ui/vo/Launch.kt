package com.example.sergeykuchin.spacexrockets.ui.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch")
data class Launch(

    @PrimaryKey
    val flightNumber: Int,
    val rocketId: String,
    val missionName: String = "",
    val dateString: String = "",
    val year: String = "",
    val isSuccessful: String = "",
    val patchSmallUrl: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Launch

        if (flightNumber != other.flightNumber) return false
        if (rocketId != other.rocketId) return false
        if (missionName != other.missionName) return false
        if (dateString != other.dateString) return false
        if (year != other.year) return false
        if (isSuccessful != other.isSuccessful) return false
        if (patchSmallUrl != other.patchSmallUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = flightNumber
        result = 31 * result + rocketId.hashCode()
        result = 31 * result + missionName.hashCode()
        result = 31 * result + dateString.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + isSuccessful.hashCode()
        result = 31 * result + (patchSmallUrl?.hashCode() ?: 0)
        return result
    }
}