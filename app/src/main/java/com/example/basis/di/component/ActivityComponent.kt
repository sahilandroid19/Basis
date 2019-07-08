package com.example.basis.di.component

import com.example.basis.di.module.ActivityModule
import com.example.basis.ui.swipe.SwipeActivity
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(swipeActivity: SwipeActivity)
}