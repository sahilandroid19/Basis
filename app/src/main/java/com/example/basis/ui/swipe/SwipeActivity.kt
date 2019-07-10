package com.example.basis.ui.swipe

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.basis.R
import com.example.basis.di.component.ActivityComponent
import com.example.basis.ui.base.BaseActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import javax.inject.Inject

class SwipeActivity : BaseActivity<SwipeViewModel>(), CardStackListener, View.OnClickListener {

    @Inject
    lateinit var adapter: SwipeAdapter

    private val manager by lazy { CardStackLayoutManager(this, this) }

    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val scoreView by lazy { findViewById<TextView>(R.id.scoreView) }

    override fun setUpView(savedInstanceState: Bundle?) {
        scoreView.text = viewModel.getDefaultScore()

        findViewById<ImageButton>(R.id.refresh).setOnClickListener(this)
        findViewById<ImageButton>(R.id.back).setOnClickListener(this)
        findViewById<ImageButton>(R.id.forward).setOnClickListener(this)

        initialize()
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
        scoreView.text = "${position+1}/${viewModel.getCardsSize()}"
    }

    override fun onCardRewound() {}

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
            R.id.back -> cardStackView.rewind()
            R.id.forward -> cardStackView.swipe()
            R.id.refresh -> adapter.notifyDataSetChanged()
        }
    }
}
