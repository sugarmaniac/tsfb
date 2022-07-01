package com.sugarmaniac.timeSeries.composeui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sugarmaniac.timeSeries.BooleanData
import com.sugarmaniac.timeSeries.FloatData
import com.sugarmaniac.timeSeries.R
import com.sugarmaniac.timeSeries.StringData
import com.sugarmaniac.timeSeries.db.dataEntity.BoolEntity
import com.sugarmaniac.timeSeries.db.dataEntity.NumericEntity
import com.sugarmaniac.timeSeries.db.dataEntity.TextEntity
import com.sugarmaniac.timeSeries.db.dataEntity.TitleEntity


@Composable
fun TextEntryFragmentScreen(title : String,
                            textList : MutableList<TextEntity>?,
                            modifier: Modifier = Modifier,
                            onEntryAdded : (text : String) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val marginLarge = dimensionResource(id = R.dimen.margin32)
        val marginMed = dimensionResource(id = R.dimen.margin16)
        
        Spacer(modifier = Modifier.height(marginLarge))
        titleView(title = title)
        Spacer(modifier = Modifier.height(marginMed))
        stringInput(okButtonClicked = onEntryAdded)
        Spacer(modifier = Modifier.height(marginMed))
        StringList(list = textList)
    }
}



@Composable
fun NumericEntryFragmentScreen(title : String,
                               numberList : MutableList<NumericEntity>?,
                               modifier: Modifier = Modifier,
                               onEntryAdded : (number : Float) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val marginLarge = dimensionResource(id = R.dimen.margin32)
        val marginMed = dimensionResource(id = R.dimen.margin16)

        Spacer(modifier = Modifier.height(marginLarge))
        titleView(title = title)
        Spacer(modifier = Modifier.height(marginMed))
        numericInput(okButtonClicked = onEntryAdded)
        Spacer(modifier = Modifier.height(marginMed))
        FloatList(list = numberList)
    }
}



@Composable
fun BoolEntryFragmentScreen(title : String,
                            boolList : MutableList<BoolEntity>?,
                            modifier: Modifier = Modifier,
                            onEntryAdded : (bool: Boolean) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val marginLarge = dimensionResource(id = R.dimen.margin32)
        val marginMed = dimensionResource(id = R.dimen.margin16)

        Spacer(modifier = Modifier.height(marginLarge))
        titleView(title = title)
        Spacer(modifier = Modifier.height(marginMed))
        booleanInput(okButtonClicked = onEntryAdded)
        Spacer(modifier = Modifier.height(marginMed))
        BooleanList(list = boolList)
    }
}

@Composable
fun entryScreen(title: String,
                type:Int,
                textEdit : (String)->Unit,
                numberEdit : (Float) -> Unit,
                boolEdit : (Boolean) -> Unit){
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 100.dp)) {
        when(type){
            0 ->{
                TextEntryFragmentScreen(modifier = Modifier.fillMaxWidth(0.8F), title = title, textList = null){
                    textEdit(it)
                }
            }
            1 ->{
                NumericEntryFragmentScreen(modifier = Modifier.fillMaxWidth(0.8F), title = title, numberList = null){
                    numberEdit(it)
                }
            }
            2 ->{
                BoolEntryFragmentScreen(modifier = Modifier.fillMaxWidth(0.8F), title = title, boolList = null){
                    boolEdit(it)
                }
            }
        }
    }
}

@Composable
fun CurrentSeriesFragmentScreen(titleList: MutableList<TitleEntity>?,
                                newEntryClicked: (title: String, type : Int) -> Unit,
                                showSerieClicked: (title: String, type : Int) -> Unit
){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val margin = dimensionResource(id = R.dimen.margin16)
        val fButton = createRef()

        fillAllButton(modifier = Modifier.constrainAs(fButton){
            top.linkTo(parent.top, margin = margin)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }){

        }


        Column(
            Modifier
                .constrainAs(createRef()) {
                    top.linkTo(fButton.bottom, margin = margin)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
            when (titleList){
                null -> {
                    Text(text = stringResource(id = R.string.there_is_no_series), color = Color.Red,
                        fontSize = dimensionResource(id = R.dimen.text32).value.sp
                    )
                }
                else -> {
                    for (item in titleList){
                        ListRowItem(titleEntity = item, showClicked = showSerieClicked, newEntryClicked = newEntryClicked)
                    }
                }
            }
        }
    }
    

}

@Composable
fun ShowTextSeries(title : String,
                    textList: MutableList<TextEntity>?){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val marginLarge = dimensionResource(id = R.dimen.margin32)
        val marginMed = dimensionResource(id = R.dimen.margin16)

        Spacer(modifier = Modifier.height(marginLarge))
        titleView(title = title)
        Spacer(modifier = Modifier.height(marginMed))
        StringList(list = textList)
    }
}

@Composable
fun ShowNumberSeries(title : String,
                    numberList: MutableList<NumericEntity>?){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val marginLarge = dimensionResource(id = R.dimen.margin32)
        val marginMed = dimensionResource(id = R.dimen.margin16)
        val marginSmall = dimensionResource(id = R.dimen.margin8)

        Spacer(modifier = Modifier.height(marginMed))
        titleView(title = title)
        Spacer(modifier = Modifier.height(marginMed))
        FloatList(list = numberList, modifier = Modifier.fillMaxHeight(0.4F))
        Spacer(modifier = Modifier.height(marginSmall))
        NumericalGraph(list = numberList!!.toList(), modifier = Modifier.fillMaxHeight(0.8F))
    }
}

@Composable
fun ShowBoolSeries(title : String,
                     boolList: MutableList<BoolEntity>?){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val marginLarge = dimensionResource(id = R.dimen.margin32)
        val marginMed = dimensionResource(id = R.dimen.margin16)
        val marginSmall = dimensionResource(id = R.dimen.margin8)

        Spacer(modifier = Modifier.height(marginMed))
        titleView(title = title)
        Spacer(modifier = Modifier.height(marginMed))
        BooleanList(list = boolList, modifier = Modifier.fillMaxHeight(0.4F))
        Spacer(modifier = Modifier.height(marginSmall))
        BooleanPieChart(list = boolList!!.toList(), modifier = Modifier.fillMaxHeight(0.8F))
    }
}



//////// PREVIEW

@Preview
@Composable
fun PreviewTextScreen(){
    TextEntryFragmentScreen(title = "title", textList = null, modifier = Modifier.fillMaxSize()) {
    }
}

@Preview
@Composable
fun PreviewNumberScreen(){
    NumericEntryFragmentScreen(title = "title", numberList = null, modifier = Modifier.fillMaxSize()) {
    }
}

@Preview
@Composable
fun PreviewBoolScreen(){
    BoolEntryFragmentScreen(title = "title", boolList = null, modifier = Modifier.fillMaxSize()) {
    }
}