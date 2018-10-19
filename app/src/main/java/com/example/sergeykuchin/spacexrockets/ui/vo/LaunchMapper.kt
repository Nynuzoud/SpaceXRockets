package com.example.sergeykuchin.spacexrockets.ui.vo

import com.example.sergeykuchin.spacexrockets.other.DateFormatter
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchDTO
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchStates
import io.reactivex.functions.Function

class LaunchMapper(private val dateFormatter: DateFormatter) : Function<LaunchDTO, Launch> {

    override fun apply(t: LaunchDTO): Launch? {
        return Launch(
            flightNumber = t.flight_number,
            rocketId = t.rocket?.rocket_id ?: return null,
            missionName = t.mission_name,
            dateString = dateFormatter.formatFromServerFormatToUserFormat(t.launch_date_local),
            year = dateFormatter.formatFromServerFormatToYearOnly(t.launch_date_local),
            isSuccessful = when(t.launch_success) {
                true -> LaunchStates.SUCCESS.text
                false -> LaunchStates.UNSUCCESS.text
                null -> LaunchStates.EMPTY.text
            },
            patchSmallUrl = t.links.mission_patch
        )
    }
}