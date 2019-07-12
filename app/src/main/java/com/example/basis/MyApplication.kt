package com.example.basis

import android.app.Application
import com.example.basis.di.component.ApplicationComponent
import com.example.basis.di.component.DaggerApplicationComponent
import com.example.basis.di.module.ApplicationModule
import com.example.basis.utils.network.NetworkHelper
import javax.inject.Inject

class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    fun getDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}