package com.sugarmaniac.timeSeries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sugarmaniac.timeSeries.R
import com.sugarmaniac.timeSeries.composeui.*
import com.sugarmaniac.timeSeries.db.dataEntity.BoolEntity
import com.sugarmaniac.timeSeries.db.dataEntity.NumericEntity
import com.sugarmaniac.timeSeries.db.dataEntity.TextEntity
import com.sugarmaniac.timeSeries.db.dataEntity.TitleEntity
import com.sugarmaniac.timeSeries.db.viewModels.BoolViewModel
import com.sugarmaniac.timeSeries.db.viewModels.NumericViewModel
import com.sugarmaniac.timeSeries.db.viewModels.TextViewModel
import com.sugarmaniac.timeSeries.db.viewModels.TitleViewModel
import java.util.*


class NewSeriesFragment : Fragment() {

    private lateinit var titleViewModel: TitleViewModel
    private lateinit var boolViewModel: BoolViewModel
    private lateinit var numericViewModel: NumericViewModel
    private lateinit var textViewModel: TextViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        boolViewModel = ViewModelProvider(this).get(BoolViewModel::class.java)
        numericViewModel = ViewModelProvider(this).get(NumericViewModel::class.java)
        textViewModel = ViewModelProvider(this).get(TextViewModel::class.java)

        return ComposeView(requireContext()).apply {
            setContent {
                val currentScreen = remember { mutableStateOf(false) }
                val type = remember { mutableStateOf(0) }
                val title = remember { mutableStateOf("") }

                when (currentScreen.value) {
                    false -> NewSeriesScreen(typeSelected = {it, it2 -> type.value = it
                        title.value = it2
                        currentScreen.value = true
                    })
                    true -> entryScreen(
                        title = title.value,
                        type = type.value,
                        textEdit = {createText(it, title.value)},
                        numberEdit = {createNumber(it, title.value)},
                        boolEdit = {createBool(it, title.value)}
                    )
                }
            }
        }
    }

    private fun createBool(value: Boolean, title: String) {
        val date = Calendar.getInstance().time.toString()

        val titleEntity = TitleEntity(uid = 0, title = title, type = BOOL_TYPE)
        titleViewModel.addTitle(titleEntity = titleEntity)

        val boolEntity = BoolEntity(uid = 0, title = title, date = date, value = value)
        boolViewModel.addBool(boolEntity = boolEntity)

        navigateToCurrentSeries()
    }

    private fun createNumber(value: Float, title: String) {
        val date = Calendar.getInstance().time.toString()

        val titleEntity = TitleEntity(uid = 0, title = title, type = NUMBER_TYPE)
        titleViewModel.addTitle(titleEntity = titleEntity)

        val numericEntity = NumericEntity(uid = 0, title = title, date = date, value = value)
        numericViewModel.addNumeric(numericEntity = numericEntity)

        navigateToCurrentSeries()

    }

    private fun createText(value: String, title: String) {
        val date = Calendar.getInstance().time.toString()

        val titleEntity = TitleEntity(uid = 0, title = title, type = TEXT_TYPE)
        titleViewModel.addTitle(titleEntity = titleEntity)

        val textEntity = TextEntity(uid = 0, value = value, date = date, title = title)
        textViewModel.addText(textEntity = textEntity)

        navigateToCurrentSeries()
    }

    private fun navigateToCurrentSeries(){
        findNavController().navigate(R.id.action_newSeriesFragment_to_currentSeriesFragment)
    }

}