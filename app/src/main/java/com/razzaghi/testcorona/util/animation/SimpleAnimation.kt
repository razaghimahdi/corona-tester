package com.razzaghi.testcorona.util.animation

import android.view.View
import android.view.animation.*
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SimpleAnimation {



    fun infinityFadeInFadeOut(view: View) {
        CoroutineScope(Dispatchers.Main).launch {

            val fadeIn = AlphaAnimation(0f, 1f)
            fadeIn.interpolator = DecelerateInterpolator() //add this
            fadeIn.duration = 1000

            val fadeOut = AlphaAnimation(1f, 0f)
            fadeOut.interpolator = AccelerateInterpolator() //and this
            fadeOut.startOffset = 1000
            fadeOut.duration = 1000

            val animation = AnimationSet(false) //change to false
            animation.addAnimation(fadeIn)
            animation.addAnimation(fadeOut)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    view.startAnimation(animation)
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })

            view.animation = animation

        }
    }


    fun TextView.setTextAnimation(
        text: String,
        duration: Long = 250,
        completion: (() -> Unit)? = null
    ) {
        fadeTextOutAnimation(duration) {
            this.text = text
            fadeTextInAnimation(duration) {
                completion?.let {
                    it()
                }
            }
        }
    }


    fun View.fadeTextOutAnimation(duration: Long = 250, visibility: Int = View.INVISIBLE, completion: (() -> Unit)? = null) {
        animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction {
                this.visibility = visibility
                completion?.let {
                    it()
                }
            }
    }

    fun View.fadeTextInAnimation(duration: Long = 250, completion: (() -> Unit)? = null) {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration)
            .withEndAction {
                completion?.let {
                    it()
                }
            }
    }

}