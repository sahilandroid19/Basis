package com.example.basis.di.module

import android.app.Activity
import com.example.basis.di.qualifier.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    @ActivityContext
    fun provideContext() = activity
}