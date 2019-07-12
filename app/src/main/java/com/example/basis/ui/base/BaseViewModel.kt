package com.example.basis.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basis.data.remote.NetworkService
import com.example.basis.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

/*
Base class for all ViewModels containing common code
 */
abstract class BaseViewModel(private val networkHelper: NetworkHelper,
                             private val compositeDisposable: CompositeDisposable,
                             private val networkService: NetworkService) : ViewModel() {

    val messageString = MutableLiveData<String>()

    /*
    Function to check internet connection
     */
    fun checkInternetConnection() : Boolean {
        return if(networkHelper.isNetworkConnected()){
            true
        }else{
            messageString.postValue("Please check your internet connection!!")
            false
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    abstract fun onCreate()
}