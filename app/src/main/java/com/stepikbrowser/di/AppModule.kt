package com.stepikbrowser.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stepikbrowser.data.stepik.di.ApiRetrofit
import com.stepikbrowser.data.stepik.di.AuthRetrofit
import com.stepikbrowser.data.stepik.util.AccessTokenInterceptor
import com.stepikbrowser.core.util.UTCDateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.Date

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }
    @Provides
    @Singleton
    @ApiRetrofit
    fun provideApiRetrofit(sharedPreferences: SharedPreferences): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, UTCDateAdapter())
            .create()
        val client = OkHttpClient.Builder()
            .addInterceptor(AccessTokenInterceptor(sharedPreferences))
            .build()
        return Retrofit.Builder()
            .baseUrl("https://stepik.org/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, UTCDateAdapter())
            .create()


        return Retrofit.Builder()
            .baseUrl("https://stepik.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}

