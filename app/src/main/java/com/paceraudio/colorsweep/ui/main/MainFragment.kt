package com.paceraudio.colorsweep.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.paceraudio.colorsweep.R
import com.paceraudio.wire.Color
import com.paceraudio.wire.MAX

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var messageTv: TextView
    private lateinit var messageTv2: TextView
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageTv = view.findViewById(R.id.message)
        messageTv2 = view.findViewById(R.id.message2)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.obtainColor().observe(
            viewLifecycleOwner,
            { color ->
                println("view on color changed: $color")
                color?.let { c ->
                    // TODO move this into VM coroutine off of main thread
                    val a = c.alpha shl 24
                    val r = c.red shl 16
                    val g = c.green shl 8
                    val shifted = a or r or g or c.blue

                    val r2 = (MAX - c.red)
                    val g2 = (MAX - c.green)
                    val b2 = (MAX - c.blue)
                    val rc = r2 shl 16
                    val gc = g2 shl 8

                    val compliment = a or rc or gc or b2

                    messageTv.text = color.toString()
                    messageTv.setTextColor(compliment)
                    messageTv.setBackgroundColor(shifted)

                    val color2 = Color(c.alpha, r2, g2, b2)
                    messageTv2.text = color2.toString()
                    messageTv2.setTextColor(shifted)
                    messageTv2.setBackgroundColor(compliment)
                }

            }
        )
        viewModel.loadInitialColors()
        viewModel.startSweep()
    }
}