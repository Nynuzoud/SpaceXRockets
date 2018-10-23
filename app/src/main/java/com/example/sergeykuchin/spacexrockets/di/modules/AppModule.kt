package com.example.sergeykuchin.spacexrockets.di.modules

import android.app.Application
import com.example.sergeykuchin.spacexrockets.BuildConfig
import com.example.sergeykuchin.spacexrockets.other.errorhandler.SimpleErrorHandler
import com.example.sergeykuchin.spacexrockets.other.errorhandler.SimpleErrorHandlerImpl
import com.example.sergeykuchin.spacexrockets.other.preferences.Preferences
import com.example.sergeykuchin.spacexrockets.other.preferences.PreferencesImpl
import com.example.sergeykuchin.spacexrockets.repository.api.Api
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchRepository
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchRepositoryImpl
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepository
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepositoryImpl
import com.example.sergeykuchin.spacexrockets.repository.db.AppDatabase
import com.example.sergeykuchin.spacexrockets.repository.db.LaunchDAO
import com.example.sergeykuchin.spacexrockets.repository.db.RocketDAO
import com.google.gson.GsonBuilder
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    fun providePicassoWithCache(app: Application): Picasso {

        //Picasso cache
        val httpCacheDirectory = File(app.cacheDir, "picassoBuilder-cache")
        val cache = Cache(httpCacheDirectory, 15 * 1024 * 1024)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .build()

        return Picasso.Builder(app)
            .downloader(
                OkHttp3Downloader(okHttpClient)
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideSimpleErrorHandler(): SimpleErrorHandler = SimpleErrorHandlerImpl()

    @Singleton
    @Provides
    fun providePreferences(app: Application): Preferences = PreferencesImpl(app)

    ///////////////////API//////////////////////

    @Singleton
    @Provides
    fun provideApiInterface(): Api {
        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.HEADERS
        }

        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val gson = GsonBuilder()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(Api::class.java)
    }

    ///////////////////REPOSITORIES//////////////////////

    @Provides
    @Singleton
    fun providesRocketRepository(api: Api, rocketDAO: RocketDAO): RocketRepository = RocketRepositoryImpl(api, rocketDAO)

    @Provides
    @Singleton
    fun providesLaunchRepository(api: Api, launchDAO: LaunchDAO): LaunchRepository = LaunchRepositoryImpl(api, launchDAO)

    //////////////////////////DB//////////////////////////

    @Provides
    @Singleton
    fun provideDB(app: Application): AppDatabase = AppDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideRocketDao(appDatabase: AppDatabase): RocketDAO = appDatabase.rocketDao()

    @Provides
    @Singleton
    fun provideLaunchDao(appDatabase: AppDatabase): LaunchDAO = appDatabase.launchDao()
}