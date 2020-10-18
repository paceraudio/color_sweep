package com.paceraudio.colorsweep.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paceraudio.colorsweep.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var colorVw1: View
    private lateinit var colorVw2: View
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorVw1 = view.findViewById(R.id.colorVw1)
        colorVw2 = view.findViewById(R.id.colorVw2)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.colors.observe(
            viewLifecycleOwner,
            { colors ->
                if (colors.size >= 2) {
                    val color = colors[0]
                    val compliment = colors[1]
                    colorVw1.setBackgroundColor(color.color)
                    colorVw2.setBackgroundColor(compliment.color)
                }
            }
        )
        viewModel.startSweep()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopSweep()
    }
}