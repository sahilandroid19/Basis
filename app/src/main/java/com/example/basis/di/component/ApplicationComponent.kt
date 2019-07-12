package com.example.basis.di.component

import com.example.basis.MyApplication
import com.example.basis.data.remote.NetworkService
import com.example.basis.di.module.ApplicationModule
import com.example.basis.utils.network.NetworkHelper
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(myApplication: MyApplication)

    fun getNetworkHelper() : NetworkHelper

    fun getCompositeDisposable() : CompositeDisposable

    fun getNetworkService() : NetworkService

}