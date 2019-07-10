package com.example.basis.ui.swipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.basis.R
import com.example.basis.di.component.ActivityComponent
import com.example.basis.ui.base.BaseActivity
import com.example.basis.utils.common.OnSwipeListener
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import javax.inject.Inject

class SwipeActivity : BaseActivity<SwipeViewModel>(), CardStackListener, View.OnClickListener, View.OnTouchListener {

    @Inject
    lateinit var adapter: SwipeAdapter

    private lateinit var gestureDetector: GestureDetectorCompat
    private var cardPosition = 0

    private val manager by lazy { CardStackLayoutManager(this, this) }

    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val scoreView by lazy { findViewById<TextView>(R.id.scoreView) }

    @SuppressLint("ClickableViewAccessibility")
    override fun setUpView(savedInstanceState: Bundle?) {
        gestureDetector = GestureDetectorCompat(this, object : OnSwipeListener() {
            override fun onSwipe(direction: Direction?): Boolean {
                if(direction == Direction.down) cardStackView.rewind()
                return true
            }
        })
        findViewById<ConstraintLayout>(R.id.base_layout).setOnTouchListener(this)

        scoreView.text = viewModel.getDefaultScore()

        findViewById<ImageButton>(R.id.refresh).setOnClickListener(this)
        findViewById<ImageButton>(R.id.back).setOnClickListener(this)
        findViewById<ImageButton>(R.id.forward).setOnClickListener(this)

        initialize()
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(p1)
        return true
    }

    override fun setUpObservers() {
        super.setUpObservers()
        viewModel.getCardsData().observe(this, Observer {
            adapter.setCards(it)
            cardStackView.adapter = adapter
        })
    }

    override fun provideLayoutId() = R.layout.content_swipe

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    /*
    Function to make activity full screen
     */
    override fun makeFullScreenActivity() {
        super.makeFullScreenActivity()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    override fun onCardDisappeared(view: View?, position: Int) {}

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {
        cardPosition = position+1
        scoreView.text = "${position+1}/${viewModel.getCardsSize()}"
    }

    override fun onCardRewound() {}

    /*
    Function to set up CardStackView
     */
    private fun initialize() {
        cardStackView.layoutManager = viewModel.getCardStackManager(manager)
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.back -> {
                if(cardPosition == 1) showToast("At Starting card can't move previous")
                else cardStackView.rewind()
            }
            R.id.forward -> {
                if(cardPosition == viewModel.getCardsSize()) showToast("No Further Cards")
                else cardStackView.swipe()
            }
            R.id.refresh -> adapter.notifyDataSetChanged()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}
