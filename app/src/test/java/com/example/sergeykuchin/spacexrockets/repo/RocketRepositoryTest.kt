package com.example.sergeykuchin.spacexrockets.repo

import com.example.sergeykuchin.spacexrockets.RxImmediateSchedulerRule
import com.example.sergeykuchin.spacexrockets.repository.api.Api
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.EnginesDTO
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketDTO
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepositoryImpl
import com.example.sergeykuchin.spacexrockets.repository.db.RocketDAO
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

class RocketRepositoryTest {

    private var rocketDTOList: MutableList<RocketDTO> = ArrayList()
    private var rocketList: MutableList<Rocket> = ArrayList()
    private var rocketDTOEmptyList: MutableList<RocketDTO> = ArrayList()

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
    lateinit var rocketDAO: RocketDAO

    @InjectMocks
    lateinit var rocketRepository: RocketRepositoryImpl

    @Before
    fun init() {

        rocketDTOList.add(
            RocketDTO(
                rocket_id = "falcon1",
                id = 1,
                rocket_name = "Falcon 1",
                country = "Republic of the Marshall Islands",
                engines = EnginesDTO(
                    number = 1
                ),
                active = false,
                flickr_images = arrayOf("testImageHere").toMutableList()
            )
        )

        rocketDTOList.add(
            RocketDTO(
                rocket_id = "falcon9",
                id = 2,
                rocket_name = "Falcon 9",
                country = "United States",
                engines = EnginesDTO(
                    number = 9
                ),
                active = true,
                flickr_images = arrayOf("testImageHere2").toMutableList()
            )
        )

        rocketList.add(
            Rocket(
                rocketId = "falcon1",
                id = 1,
                rocketName = "Falcon 1",
                country = "Republic of the Marshall Islands",
                enginesNumber = 1,
                active = false,
                imageUrl = "testImageHere"
            )
        )

        rocketList.add(
            Rocket(
                rocketId = "falcon9",
                id = 2,
                rocketName = "Falcon 9",
                country = "United States",
                enginesNumber = 9,
                active = true,
                imageUrl = "testImageHere2"
            )
        )
    }

    @Test
    fun checkRocketRepositoryFinishesSuccessfully() {
        `when`(api.getAllRockets()).thenReturn(Single.just(rocketDTOList))

        var result: MutableList<Rocket>? = null
        var error: Throwable? = null

        rocketRepository
            .getAllRocketsFromApi(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result = it.toMutableList()
            }, {
                error = it
            })

        assert(result != null)
        assert(result?.isNotEmpty() == true)
        assert(result == rocketList)
        assert(error == null)
    }

    @Test
    fun checkRocketRepositoryFinishesWithError() {
        `when`(api.getAllRockets()).thenReturn(Single.just(rocketDTOEmptyList))

        var result: MutableList<Rocket>? = null
        var error: Throwable? = null

        rocketRepository
            .getAllRocketsFromApi(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result = it.toMutableList()
            }, {
                error = it
            })

        assert(result == null)
        assert(error != null)
    }
}