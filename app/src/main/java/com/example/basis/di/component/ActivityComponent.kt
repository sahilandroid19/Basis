package com.example.basis.di.component

import com.example.basis.di.module.ActivityModule
import com.example.basis.di.scope.ActivityScope
import com.example.basis.ui.swipe.SwipeActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(swipeActivity: SwipeActivity)
}