package com.example.basis.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.basis.MyApplication
import com.example.basis.di.component.ActivityComponent
import com.example.basis.di.component.DaggerActivityComponent
import com.example.basis.di.module.ActivityModule
import javax.inject.Inject

/*
Base class for all Activities containing common code
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(){

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        makeFullScreenActivity()
        setContentView(provideLayoutId())
        setUpObservers()
        if(viewModel.checkInternetConnection()) viewModel.onCreate()
        setUpView(savedInstanceState)
    }

    /*
    Function to set observers to live data's
     */
    protected open fun setUpObservers() {
        viewModel.messageString.observe(this, Observer {
          Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    protected open fun makeFullScreenActivity() {}

    protected abstract fun setUpView(savedInstanceState: Bundle?)

    @LayoutRes
    protected abstract fun provideLayoutId() : Int

    /*
    Function to building up Dagger activity component-common to all
     */
    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MyApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)
}