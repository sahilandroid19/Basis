package com.example.basis.ui.swipe

import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.MutableLiveData
import com.example.basis.data.model.Card
import com.example.basis.data.remote.NetworkService
import com.example.basis.ui.base.BaseViewModel
import com.example.basis.utils.network.NetworkHelper
import com.yuyakaido.android.cardstackview.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/*
ViewModel class for SwipeActivity
 */

class SwipeViewModel @Inject constructor
    (private val networkHelper: NetworkHelper,
     private val compositeDisposable: CompositeDisposable,
     private val networkService: NetworkService) : BaseViewModel(networkHelper, compositeDisposable, networkService){

    private var cardSize = 0
    private val cardsData = MutableLiveData<List<Card>>()

    override fun onCreate() {
        getCardsFromWeb()
    }

    fun getCardsSize() = cardSize
    fun getCardsData() = cardsData

    /*
    Function to fetch cards data from API
     */
    private fun getCardsFromWeb() {
        compositeDisposable.add(
            networkService.getCardsFromWeb()
                .subscribeOn(Schedulers.io())
                .subscribe
                    ({
                    cardSize = it.cards.size
                    cardsData.postValue(it.cards)
                    },
                    {
                        Log.d("error", it.toString())
                    })
        )
    }

    /*
    Function to setup CardStackManager
     */
    fun getCardStackManager(manager: CardStackLayoutManager) : CardStackLayoutManager{
        return manager.apply {
            setStackFrom(StackFrom.Bottom)
            setTranslationInterval(4.0f)
            setSwipeThreshold(0.2f)
            setMaxDegree(80.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(false)
            setRewindAnimationSetting(getRewindAnimationSetting())
            setSwipeAnimationSetting(getSwipeAnimationSetting())
            setOverlayInterpolator(AccelerateInterpolator())
        }
    }

    /*
    Function to set animation for rewind
     */
    private fun getRewindAnimationSetting(): RewindAnimationSetting =
        RewindAnimationSetting
            .Builder()
            .setDirection(Direction.Right)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(DecelerateInterpolator())
            .build()

    /*
    Function to set animation for next card
     */
    private fun getSwipeAnimationSetting(): SwipeAnimationSetting =
        SwipeAnimationSetting
            .Builder()
            .setDirection(Direction.Right)
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()

    fun getDefaultScore() = "1/${cardSize}"

}