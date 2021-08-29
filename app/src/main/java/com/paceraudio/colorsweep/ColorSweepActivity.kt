package com.paceraudio.colorsweep

import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.paceraudio.colorsweep.ui.views.DualColorScreen
import com.paceraudio.colorsweep.ui.viewmodels.ColorSweepViewModel
import com.paceraudio.colorsweep.util.UiUtil
import com.paceraudio.wire.COLOR_TAG
import com.paceraudio.wire.models.ColorData
import com.paceraudio.wire.models.MAX
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = COLOR_TAG

@AndroidEntryPoint
class ColorSweepActivity : AppCompatActivity() {
    private val colorViewModel by viewModels<ColorSweepViewModel>()
    @Inject
    lateinit var uiUtil: UiUtil

    private val className = this::class.java.simpleName

    override fun onStart() {
        super.onStart()
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            this.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            supportActionBar?.hide(); // hide the title bar
            this.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            ); //enable full screen
        }

        val size = uiUtil.calcCircleSizeInPx()
        if (BuildConfig.DEBUG) {
            Log.d(COLOR_TAG, "$className size in px: $size")
        }
        colorViewModel.setUp()
        setContent {
            Surface {
                FirstColorScreen(colorViewModel = colorViewModel, size)
            }
        }
    }

    @Composable
    private fun FirstColorScreen(colorViewModel: ColorSweepViewModel, circleSizePx: Float) {
        /* TODO figure this out, when we observe a list, this only runs when the size changes.
        *   We are only observing the second color so that we still only run this once both colors change.*/
        val color2: ColorData by colorViewModel.color2.observeAsState(
            initial = ColorData(MAX, MAX, MAX, MAX)
        )
        colorViewModel.color1.value?.let { c1 ->
            DualColorScreen(
                colorList = listOf(c1, color2),
                sizeDp = Dp(circleSizePx),
                modifier = Modifier,
                onClick = { colorViewModel.onClick() }
            )
        }
    }
}