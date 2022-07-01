package com.sugarmaniac.timeSeries.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.sugarmaniac.timeSeries.R
import com.sugarmaniac.timeSeries.composeui.MainScreen

class OpenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                setBackgroundColor(Color.DKGRAY)
                MainScreen(
                    newSeriesClicked = { openNewSeries() },
                    newEntryClicked = { openCurrentSeries() },
                    settingClicked = { openSetting() }
                )
            }
        }
    }

    private fun openSetting() {
        findNavController().navigate(R.id.action_openFragment_to_settingsFragment)
    }

    private fun openCurrentSeries() {
        findNavController().navigate(R.id.action_openFragment_to_currentSeriesFragment)
    }

    private fun openNewSeries() {
        findNavController().navigate(R.id.action_openFragment_to_newSeriesFragment)
    }

}