package com.example.sergeykuchin.spacexrockets.repo

import com.example.sergeykuchin.spacexrockets.RxImmediateSchedulerRule
import com.example.sergeykuchin.spacexrockets.repository.api.Api
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchDTO
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchRepositoryImpl
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchStates
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LinksDTO
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketDTO
import com.example.sergeykuchin.spacexrockets.repository.db.LaunchDAO
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

class LaunchRepositoryTest {

    private var launchDTOList: MutableList<LaunchDTO> = ArrayList()
    private var launchList: MutableList<Launch> = ArrayList()
    private var launchDTOEmptyList: MutableList<LaunchDTO> = ArrayList()

    companion object {
        @JvmStatic
        @get:ClassRule
        val schedulers = RxImmediateSchedulerRule()
    }


    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var api: Api

    @Mock
    lateinit var launchDAO: LaunchDAO

    @InjectMocks
    lateinit var launchRepository: LaunchRepositoryImpl

    @Before
    fun init() {

        launchDTOList.add(
            LaunchDTO(
                flight_number = 1,
                rocket = RocketDTO(
                    rocket_id = "falcon1"
                ),
                mission_name = "FalconSat",
                launch_date_local = "2006-03-25T10:30:00+12:00",
                launch_success = false,
                links = LinksDTO(
                    mission_patch = "testImageHere"
                )
            )
        )

        launchDTOList.add(
            LaunchDTO(
                flight_number = 21,
                rocket = RocketDTO(
                    rocket_id = "falcon9"
                ),
                mission_name = "ABS-3A / Eutelsat 115W B",
                launch_date_local = "2015-03-02T23:50:00-04:00",
                launch_success = true,
                links = LinksDTO(
                    mission_patch = "testImageHere2"
                )
            )
        )

        launchList.add(
            Launch(
                flightNumber = 1,
                rocketId = "falcon1",
                missionName = "FalconSat",
                dateString = "25.03.2006 03:30",
                isSuccessful = LaunchStates.UNSUCCESS.text,
                patchSmallUrl = "testImageHere"
            )
        )

        launchList.add(
            Launch(
                flightNumber = 21,
                rocketId = "falcon9",
                missionName = "ABS-3A / Eutelsat 115W B",
                dateString = "03.03.2015 08:50",
                isSuccessful = LaunchStates.SUCCESS.text,
                patchSmallUrl = "testImageHere2"
            )
        )
    }

    @Test
    fun checkRocketRepositoryFinishesSuccessfully() {
        Mockito.`when`(api.getAllLaunches()).thenReturn(Single.just(launchDTOList))

        var result: MutableList<Launch>? = null
        var error: Throwable? = null

        launchRepository
            .getAllLaunchesFromApi()
            .subscribe({
                result = it
            }, {
                error = it
            })

        assert(result != null)
        assert(result?.isNotEmpty() == true)
        assert(result == launchList)
        assert(error == null)
    }

    @Test
    fun checkRocketRepositoryFinishesWithError() {
        Mockito.`when`(api.getAllLaunches()).thenReturn(Single.just(launchDTOEmptyList))

        var result: MutableList<Launch>? = null
        var error: Throwable? = null

        launchRepository
            .getAllLaunchesFromApi()
            .subscribe({
                result = it
            }, {
                error = it
            })

        assert(result == null)
        assert(error != null)
    }
}