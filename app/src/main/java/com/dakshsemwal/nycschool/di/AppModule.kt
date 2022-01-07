package com.dakshsemwal.nycschool.di

import com.dakshsemwal.nycschool.common.Common.BASE_URL
import com.dakshsemwal.nycschool.data.remote.NYCSchoolDataBaseApi
import com.dakshsemwal.nycschool.data.repository.NYCSchoolRepositoryImpl
import com.dakshsemwal.nycschool.domain.repository.NYCSchoolRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides Repository as a Singleton Instance
     */
    @Provides
    @Singleton
    fun provideMovieRepository(api: NYCSchoolDataBaseApi): NYCSchoolRepository =
        NYCSchoolRepositoryImpl(api = api)


    /**
     * Provides Api By Using Retrofit Dependency
     */
    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): NYCSchoolDataBaseApi =
        retrofit.create(NYCSchoolDataBaseApi::class.java)

    /**
     * Provides OKHttp client to be used across the app
     */
    @Singleton
    @Provides
    fun provideOKHttp(
        httpLogger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            addInterceptor(httpLogger)
        }.build()
    }

    /**
     * Provides HTTP request logger to be used across the app
     */
    @Singleton
    @Provides
    fun provideOKHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(BASE_URL)
        }.build()
    }

}