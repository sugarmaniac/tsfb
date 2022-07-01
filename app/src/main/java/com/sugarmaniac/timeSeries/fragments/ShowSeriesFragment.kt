package com.sugarmaniac.timeSeries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.sugarmaniac.timeSeries.R
import com.sugarmaniac.timeSeries.composeui.*
import com.sugarmaniac.timeSeries.db.dataEntity.BoolEntity
import com.sugarmaniac.timeSeries.db.dataEntity.NumericEntity
import com.sugarmaniac.timeSeries.db.dataEntity.TextEntity
import com.sugarmaniac.timeSeries.db.viewModels.BoolViewModel
import com.sugarmaniac.timeSeries.db.viewModels.NumericViewModel
import com.sugarmaniac.timeSeries.db.viewModels.TextViewModel

private const val TITLE = "title"
private const val TYPE = "type"

class ShowSeriesFragment : Fragment() {

    private var title: String = ""
    private var type: Int = 0
    private lateinit var textViewModel: TextViewModel
    private lateinit var numericViewModel: NumericViewModel
    private lateinit var boolViewModel: BoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE).toString()
            type = it.getInt(TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        boolViewModel = ViewModelProvider(this).get(BoolViewModel::class.java)
        numericViewModel = ViewModelProvider(this).get(NumericViewModel::class.java)
        textViewModel = ViewModelProvider(this).get(TextViewModel::class.java)

        return ComposeView(requireContext()).apply {
            setContent {

                val textList = remember { mutableStateListOf<TextEntity>() }
                val numericList = remember { mutableStateListOf<NumericEntity>() }
                val boolList = remember { mutableStateListOf<BoolEntity>() }

                val marginLarge = dimensionResource(id = R.dimen.margin32)
                val marginMed = dimensionResource(id = R.dimen.margin16)

                when (type){
                    TEXT_TYPE -> textViewModel.readAllData.observe(viewLifecycleOwner) { list ->
                        textList.clear()
                        textList.addAll(list)
                    }
                    NUMBER_TYPE -> numericViewModel.readAllData.observe(viewLifecycleOwner) { list ->
                        numericList.clear()
                        numericList.addAll(list)
                    }
                    BOOL_TYPE -> boolViewModel.readAllData.observe(viewLifecycleOwner) { list ->
                        boolList.clear()
                        boolList.addAll(list)
                    }
                }

                when(type){
                    TEXT_TYPE -> {
                        ShowTextSeries(title = title, textList = textList)
                    }
                    NUMBER_TYPE -> {
                        ShowNumberSeries(title = title, numberList = numericList)
                    }
                    BOOL_TYPE -> {
                        ShowBoolSeries(title = title, boolList = boolList)
                    }
                }

            }
        }

    }



    companion object {
        @JvmStatic fun newInstance(title: String, type: Int) =
            EntryFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putInt(TYPE, type)
                }
            }
    }
}