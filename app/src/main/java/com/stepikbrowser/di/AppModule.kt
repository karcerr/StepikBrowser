package com.stepikbrowser.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stepikbrowser.util.UTCDateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

    @Module
    @InstallIn(SingletonComponent::class)
    object FirebaseModule {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    }
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, UTCDateAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl("https://stepik.org/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
