package com.razzaghi.testcorona.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.razzaghi.testcorona.R
import com.razzaghi.testcorona.util.animation.SimpleAnimation.infinityFadeInFadeOut
import kotlinx.android.synthetic.main.splash_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.splash_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()
        startsplash()

    }

    private fun startsplash() {
        lifecycleScope.launch {
            delay(3000L)
            val action =
                SplashFragmentDirections.actionSplashFragmentToListFragment()
            findNavController().navigate(action)
        }
    }

    private fun playAnimation() {
        CoroutineScope(Dispatchers.Main).launch {
            doctorAnimation.playAnimation()

            infinityFadeInFadeOut(txtWelcome)
        }

    }
}