package com.sugarmaniac.timeSeries.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
import org.w3c.dom.Text
import java.util.*


private const val TITLE = "title"
private const val TYPE = "type"


class AddEntryFragment : Fragment() {
    // TODO: Rename and change types of parameters
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        boolViewModel = ViewModelProvider(this).get(BoolViewModel::class.java)
        numericViewModel = ViewModelProvider(this).get(NumericViewModel::class.java)
        textViewModel = ViewModelProvider(this).get(TextViewModel::class.java)

        return ComposeView(requireContext()).apply {
            setContent {
                val textList = remember{ mutableStateListOf<TextEntity>() }
                val numericList = remember{ mutableStateListOf<NumericEntity>() }
                val boolList = remember{ mutableStateListOf<BoolEntity>() }

                textViewModel.readAllByTitle(title = title).observe(viewLifecycleOwner) { list ->
                    textList.clear()
                    textList.addAll(list)
                    invalidate()
                }

                numericViewModel.readAllByTitle(title = title).observe(viewLifecycleOwner) { list ->
                    numericList.clear()
                    numericList.addAll(list)
                    invalidate()
                }

                boolViewModel.readAllByTitle(title = title).observe(viewLifecycleOwner) { list ->
                    boolList.clear()
                    boolList.addAll(list)
                    invalidate()
                }


                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    when(type){
                        0 ->{
                            TextEntryFragmentScreen(title = title, textList = textList){
                                addStringEntryAndCreateSeries(it)
                            }
                        }
                        1 ->{
                            NumericEntryFragmentScreen(title = title, numberList = numericList){
                                addNumericEntryAndCreateSeries(it)
                            }
                        }
                        2 ->{
                            BoolEntryFragmentScreen(title = title, boolList = boolList){
                                addBooleanEntryAndCreateSeries(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addStringEntryAndCreateSeries(text : String ){
        val date = Calendar.getInstance().time.toString()

        val textEntity = TextEntity(uid = 0, value = text, date = date, title = title)
        textViewModel.addText(textEntity = textEntity)

        navigateToCurrentSeries()
    }

    private fun addNumericEntryAndCreateSeries(number : Float){
        val date = Calendar.getInstance().time.toString()

        val numericEntity = NumericEntity(uid = 0, title = title, date = date, value = number)
        numericViewModel.addNumeric(numericEntity = numericEntity)

        navigateToCurrentSeries()
    }

    private fun addBooleanEntryAndCreateSeries(bool : Boolean){
        val date = Calendar.getInstance().time.toString()

        val boolEntity = BoolEntity(uid = 0, title = title, date = date, value = bool)
        boolViewModel.addBool(boolEntity = boolEntity)

        navigateToCurrentSeries()
    }

    private fun navigateToCurrentSeries() {
        findNavController().navigateUp()
    }

    companion object {
        @JvmStatic fun newInstance(title: String, type: Int) =
            AddEntryFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putInt(TYPE, type)
                }
            }
    }
}