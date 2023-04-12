package test.raul.kotlin.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import test.raul.kotlin.database.DatabaseHelper
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): DatabaseHelper{
        return DatabaseHelper.getInstance(context)
    }
}