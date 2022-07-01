package com.sugarmaniac.timeSeries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sugarmaniac.timeSeries.R
import com.sugarmaniac.timeSeries.composeui.CurrentSeriesFragmentScreen
import com.sugarmaniac.timeSeries.db.dataEntity.TitleEntity
import com.sugarmaniac.timeSeries.db.viewModels.TitleViewModel

class CurrentSeriesFragment : Fragment() {

    lateinit var titleViewModel: TitleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val titleList = remember {mutableStateListOf<TitleEntity>()}

                titleViewModel.readAllData.observe(viewLifecycleOwner) { list ->
                    titleList.clear()
                    titleList.addAll(list)
                }

                CurrentSeriesFragmentScreen(
                    titleList = titleList,
                    newEntryClicked = { title : String, type : Int -> newEntryClicked(title, type) },
                    showSerieClicked = {title : String, type : Int -> showSerieClicked(title, type)}
                )
            }
        }
    }

    private fun showSerieClicked(title: String, type: Int) {
        val args = Bundle()
        args.putString("title", title)
        args.putInt("type", type)
        findNavController().navigate(R.id.action_currentSeriesFragment_to_showSeriesFragment, args = args)
    }

    private fun newEntryClicked(title: String, type: Int) {
        val args = Bundle()
        args.putString("title", title)
        args.putInt("type", type)
        findNavController().navigate(R.id.action_currentSeriesFragment_to_addEntryFragment, args = args)
    }

}
