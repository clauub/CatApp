package com.quess.catapp.di.modules

import com.quess.catapp.api.RequestInterceptor
import com.quess.catapp.api.service.CatApiService
import com.quess.catapp.utils.Constants
import com.quess.catapp.utils.Constants.API_KEY
import com.quess.catapp.utils.Constants.BASE_CAT_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {
    companion object {
        private const val TAG = "NetworkModule"
        private const val REQUEST_TIMEOUT_60 = 60
        private const val REQUEST_TIMEOUT_30 = 30
    }

    @Provides
    @Singleton
    fun provideCatApiOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {

        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.d(it)
        })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(logger)
            .addInterceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("x-api-key", API_KEY)
                        .build()
                chain.proceed(request)
            }
            .connectTimeout(REQUEST_TIMEOUT_30.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT_60.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT_60.toLong(), TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideCatApiService(okHttpClient: OkHttpClient): CatApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_CAT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(CatApiService::class.java)
    }


}
