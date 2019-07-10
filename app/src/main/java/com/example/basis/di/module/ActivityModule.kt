package com.example.basis.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.basis.data.remote.NetworkService
import com.example.basis.di.qualifier.ActivityContext
import com.example.basis.ui.base.BaseActivity
import com.example.basis.ui.swipe.SwipeViewModel
import com.example.basis.utils.common.ViewModelProviderFactory
import com.example.basis.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @ActivityContext
    @Provides
    fun provideContext() : Context = activity

    @Provides
    fun provideSwipeViewModel(networkHelper: NetworkHelper,
                              compositeDisposable: CompositeDisposable,
                              networkService: NetworkService) : SwipeViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(SwipeViewModel::class){
                SwipeViewModel(networkHelper, compositeDisposable, networkService)
            }).get(SwipeViewModel::class.java)

}