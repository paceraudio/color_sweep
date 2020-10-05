package com.paceraudio.colorsweep.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.MainAxisAlignment
import androidx.compose.runtime.Composable
//import androidx.compose.Composable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
//import androidx.ui.core.Text
//import androidx.ui.core.setContent
//import androidx.ui.layout.Column
//import androidx.ui.layout.LayoutSize
//import androidx.ui.layout.MainAxisAlignment
import com.paceraudio.colorsweep.R
import com.paceraudio.wire.Color
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent


class ColorSweepFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView = inflater.inflate(R.layout.color_sweep_fragment, container, false)

        (fragmentView as ViewGroup).setContent() {
            ColorSweepFragmentScreen(colorViewModel = viewModel)
        }
        return fragmentView
    }

    @Composable
    private fun ColorSweepFragmentScreen(colorViewModel: MainViewModel) {
        //val items: List<Color> by colorViewModel.colors.observeAsState(listOf())
        viewModel.colors.observe(viewLifecycleOwner, { colors ->
            Colors(color = colors[0], color2 = colors[1])
        })


//        TodoScreen(
//            items = items,
//            onAddItem = { colorViewModel.addItem(it) },
//            onRemoveItem = { colorViewModel.removeItem(it) }
//        )
    }

    @Composable
    fun Colors(color: Color, color2: Color) {

        Column(
            mainAxisAlignment = MainAxisAlignment.Center,
            mainAxisSize = LayoutSize.Expand
        ) {
            Text(text = color.toString())
            Text(text = color2.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.colors.observe(viewLifecycleOwner, { colors ->
            Colors(color = colors[0], color2 = colors[1])
        })
//        viewModel.obtainColor().observe(
//            viewLifecycleOwner,
//            { color ->
//                println("view on color changed: $color")
//                color?.let { c ->
//                    // TODO move this into VM coroutine off of main thread
//                    val a = c.alpha shl 24
//                    val r = c.red shl 16
//                    val g = c.green shl 8
//                    val shifted = a or r or g or c.blue
//
//                    val r2 = (MAX - c.red)
//                    val g2 = (MAX - c.green)
//                    val b2 = (MAX - c.blue)
//                    val rc = r2 shl 16
//                    val gc = g2 shl 8
//
//                    val compliment = a or rc or gc or b2
//
////                    messageTv.text = color.toString()
////                    messageTv.setTextColor(compliment)
////                    messageTv.setBackgroundColor(shifted)
//
//                    val color2 = Color(c.alpha, r2, g2, b2)
////                    messageTv2.text = color2.toString()
////                    messageTv2.setTextColor(shifted)
////                    messageTv2.setBackgroundColor(compliment)
//                    Colors(color = c, color2 = color2)
//                }
//
//            }
//        )
        viewModel.loadInitialColors()
        viewModel.startSweep()
    }


//    @Composable
//    fun Hello(name: String) = MaterialTheme {
//        FlexColumn {
//            inflexible {
//                // Item height will be equal content height
//                TopAppBar( // App Bar with title
//                    title = { Text("Jetpack Compose Sample") }
//                )
//            }
//            expanded(1F) {
//                // occupy whole empty space in the Column
//                Center {
//                    // Center content
//                    Text("Hello $name!") // Text label
//                }
//            }
//        }
//    }
}