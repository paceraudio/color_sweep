package com.paceraudio.colorsweep

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.Dp
import com.paceraudio.colorsweep.ui.main.DualColorScreen
import com.paceraudio.colorsweep.ui.main.MainViewModel
import com.paceraudio.wire.*

private const val TAG = COLOR_TAG

class ColorSweepActivity : AppCompatActivity() {
    private val colorViewModel by viewModels<MainViewModel>()
    private val className = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            this.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            supportActionBar?.hide(); // hide the title bar
            this.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        }

        setContent {
            Surface {
                FirstColorScreen(colorViewModel = colorViewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        colorViewModel.startSweep()
    }

    @Composable
    private fun FirstColorScreen(colorViewModel: MainViewModel) {
        /* TODO figure this out, when we observe a list, this only runs when the size changes.
        *   We are only observing the second color so that we still only run this once both colors change.*/
        val color2: ColorData by colorViewModel.color2.observeAsState(initial = ColorData(MAX, MIN, MIN, MIN))
        colorViewModel.color1.value?.let { c1 ->
            DualColorScreen(
                colorList = listOf(c1, color2),
                sizeDp = Dp(resources.getInteger(R.integer.compose_view_width_i).toFloat()),
                modifier = Modifier)
        }
    }
}