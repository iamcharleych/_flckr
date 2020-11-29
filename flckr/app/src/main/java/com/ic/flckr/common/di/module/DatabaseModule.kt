package com.ic.flckr.common.di.module

import android.content.Context
import androidx.room.Room
import com.ic.flckr.common.data.db.DATABASE_NAME
import com.ic.flckr.common.data.db.FlckrDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): FlckrDatabase {
        return Room.databaseBuilder(context, FlckrDatabase::class.java, DATABASE_NAME).build()
    }
}