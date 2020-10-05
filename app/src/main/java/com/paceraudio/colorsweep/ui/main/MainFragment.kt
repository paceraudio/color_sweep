package com.paceraudio.colorsweep.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paceraudio.colorsweep.R

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
        viewModel.colors.observe(
            viewLifecycleOwner,
            { colors ->
                if (colors.size >= 2) {
                    val color = colors[0]
                    val compliment = colors[1]
                    messageTv.text = Integer.toBinaryString(color.color)
                    messageTv.setTextColor(compliment.color)
                    messageTv.setBackgroundColor(color.color)

                    messageTv2.text = Integer.toBinaryString(compliment.color)
                    messageTv2.setTextColor(color.color)
                    messageTv2.setBackgroundColor(compliment.color)
                }
            }
        )
        viewModel.loadInitialColors()
        viewModel.startSweep()
    }
}