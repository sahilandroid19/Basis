package com.example.basis.di.module

import android.content.Context
import com.example.basis.MyApplication
import com.example.basis.data.remote.NetworkService
import com.example.basis.data.remote.Networking
import com.example.basis.di.qualifier.ApplicationContext
import com.example.basis.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {

    @ApplicationContext
    @Provides
    fun provideContext() : Context = application

    @Provides
    fun provideCompositeDisposable() : CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideNetworkService() : NetworkService = Networking.create()

    @Singleton
    @Provides
    fun provideNetworkHelper() : NetworkHelper = NetworkHelper()
}