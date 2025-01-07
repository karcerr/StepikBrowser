package com.stepikbrowser.data.local.di

import android.content.Context
import androidx.room.Room
import com.stepikbrowser.data.local.CourseDao
import com.stepikbrowser.data.local.CourseDatabase
import com.stepikbrowser.data.local.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CourseDatabase {
        return Room.databaseBuilder(
            context,
            CourseDatabase::class.java,
            "courses"
        ).build()
    }

    @Provides
    fun provideCourseDao(database: CourseDatabase): CourseDao {
        return database.courseDao()
    }
    @Provides
    fun provideLocalStorage(dao: CourseDao): LocalDataSourceImpl {
        return LocalDataSourceImpl(dao)
    }
}
