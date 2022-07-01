package com.sugarmaniac.timeSeries.composeui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.sugarmaniac.timeSeries.*
import com.sugarmaniac.timeSeries.R
import com.sugarmaniac.timeSeries.db.dataEntity.BoolEntity
import com.sugarmaniac.timeSeries.db.dataEntity.NumericEntity
import com.sugarmaniac.timeSeries.db.dataEntity.TextEntity
import com.sugarmaniac.timeSeries.db.dataEntity.TitleEntity
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import java.sql.Date

@Composable
fun CardButton(modifier : Modifier = Modifier,
              text : String,
              backgroundColor : Int,
              textColor : Int,
              textSize : Int, clicked : ()->Unit){

    Card(modifier = modifier
        .aspectRatio(1F)
        .clickable { clicked() },shape = RoundedCornerShape(8.dp),
        backgroundColor = colorResource(id = backgroundColor)) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val txt = createRef()

            Text(text = text, color = colorResource(id = textColor), fontSize = textSize.sp,
                textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(txt){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            })
        }
    }
}

@Composable
fun adsLayout(modifier : Modifier = Modifier,
                closeAds : () -> Unit){
    ConstraintLayout(modifier = modifier) {
        val (button, ads) = createRefs()

        OutlinedButton(onClick = { closeAds() },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(button) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                },
            shape = CircleShape,
            border = BorderStroke(2.dp, Color.Red),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Red)
            ) {
            Icon(Icons.Default.Close, contentDescription = null)
        }
    }
}

@Composable
fun titleView(title : String,
    modifier : Modifier = Modifier){
    ConstraintLayout(modifier = modifier.wrapContentSize()) {
        Text(text = title,
            fontSize = dimensionResource(id = R.dimen.text32).value.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White)
    }
}

@Composable
fun adsView(modifier : Modifier = Modifier){
    ConstraintLayout(modifier = modifier.wrapContentSize()) {
        AndroidView(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            ,
            factory = {
                    context ->
                AdView(context).apply {
                    adSize = AdSize.BANNER
                    adUnitId = context.getString(R.string.banner_id)
                    loadAd(AdRequest.Builder().build())
                }
            })
    }
}

@Composable
fun stringInput(modifier: Modifier = Modifier,
                backgroundColor: Color = Color.Gray,
                textColor: Color = Color.White,
                okButtonClicked : (value : String) -> Unit){
    val value = remember { mutableStateOf("")}
        Card(modifier = Modifier, backgroundColor = backgroundColor, shape = RoundedCornerShape(16.dp)) {
            ConstraintLayout(modifier = Modifier
                .fillMaxWidth(0.95F)
                .wrapContentHeight(),
            ) {
                val (tv, tfv, button ) = createRefs()

                Text(text = stringResource(id = R.string.text), color = textColor, fontSize = 20.sp,
                    modifier = Modifier.constrainAs(tv){
                        top.linkTo(parent.top, margin = 8.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    })
                TextField(value = value.value,
                    onValueChange = {value.value = it},
                    placeholder = { Text(stringResource(id =R.string.enter_value) )},
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(tfv) {
                            top.linkTo(tv.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                            end.linkTo(button.start)
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldColor()
                )
                Button(onClick = { okButtonClicked(value.value) }, modifier.constrainAs(button){
                    top.linkTo(tv.bottom, margin = 8.dp)
                    end.linkTo(parent.end)
                    start.linkTo(tfv.end)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }, colors = ButtonColor()) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "")
                }
            }
        }
}

@Composable
fun numericInput(modifier: Modifier = Modifier,
                backgroundColor: Color = Color.Gray,
                textColor: Color = Color.White,
                okButtonClicked : (value : Float) -> Unit){
    val value = remember { mutableStateOf("")}
    Card(modifier = modifier, backgroundColor = backgroundColor, shape = RoundedCornerShape(16.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth(0.95F)) {
            val (tv, tfv, button ) = createRefs()

            Text(text = stringResource(id = R.string.numeric), color = textColor, fontSize = 20.sp,
                modifier = Modifier.constrainAs(tv){
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                })
            TextField(value = value.value,
                onValueChange = {if (it.isEmpty()){
                    value.value = it
                } else {
                    value.value = when (it.toDoubleOrNull()){
                        null -> value.value
                        else -> it.toString()
                    }}},
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(tfv) {
                        top.linkTo(tv.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldColor(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(onClick = { okButtonClicked(value.value.toFloat()) }, modifier.constrainAs(button){
                top.linkTo(tv.bottom, margin = 8.dp)
                end.linkTo(parent.end)
                start.linkTo(tfv.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }, colors = ButtonColor()) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "")
            }
        }
    }
}

@Composable
fun booleanInput(modifier: Modifier = Modifier,
                 backgroundColor: Color = Color.Gray,
                 textColor: Color = Color.White,
                 okButtonClicked : (value : Boolean) -> Unit){
    var value = remember { mutableStateOf(true)}
    Card(modifier = modifier, backgroundColor = backgroundColor, shape = RoundedCornerShape(16.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth(0.95F)) {
            val (tv, tfv, button ) = createRefs()

            val trueColor = when(value.value){
                true -> Color.Red
                false -> Color.White
            }

            val falseColor = when(value.value){
                false -> Color.Red
                true -> Color.White
            }

            Text(text = stringResource(id = R.string.bool), color = textColor, fontSize = 20.sp,
                modifier = Modifier.constrainAs(tv){
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                })
            ConstraintLayout(modifier = Modifier
                .constrainAs(tfv) {
                top.linkTo(tv.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                end.linkTo(button.start)
            })
            {
                val (c1,c2) = createRefs()
                Card(modifier = Modifier
                    .width(dimensionResource(id = R.dimen.button80))
                    .aspectRatio(2F)
                    .clickable { value.value = true }
                    .constrainAs(c1) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(c2.start, margin = 16.dp)
                    }, backgroundColor = trueColor) {

                    Text(text = stringResource(id = R.string.true_string))
                }
                Card(modifier = Modifier
                    .width(dimensionResource(id = R.dimen.button80))
                    .aspectRatio(2F)
                    .clickable { value.value = false }
                    .constrainAs(c2) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(c1.end, margin = 16.dp)
                        end.linkTo(parent.end)
                    }, backgroundColor = falseColor) {
                    Text(text = stringResource(id = R.string.false_string))
                }
            }
            Button(onClick = { okButtonClicked(value.value) }, modifier.constrainAs(button){
                top.linkTo(tv.bottom, margin = 8.dp)
                end.linkTo(parent.end)
                start.linkTo(tfv.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }, colors = ButtonColor()) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "")
            }
        }
    }
}


@Composable
fun StringList( list:MutableList<TextEntity>?,
                modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()
    Column(modifier = modifier){
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(state = scrollState, enabled = true), horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text = stringResource(id = R.string.time), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
            Text(text = stringResource(id = R.string.value), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
        }
        when(list){
            null -> {}
            else -> {
                for (item in list){
                    Divider(color = Color.Black, thickness = 1.dp)
                    ListRowItem(stringEntity = item)
                }
            }
        }
    }
}

@Composable
fun FloatList( list:MutableList<NumericEntity>?,
                modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()
    Column(modifier = modifier){
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(state = scrollState, enabled = true), horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text = stringResource(id = R.string.time), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
            Text(text = stringResource(id = R.string.value), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
        }
        when(list){
            null -> {}
            else -> {
                for (item in list){
                    Divider(color = Color.Black, thickness = 1.dp)
                    ListRowItem(numericEntity = item)
                }
            }
        }
    }
}

@Composable
fun BooleanList( list:MutableList<BoolEntity>?,
                modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()
    Column(modifier = modifier){
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(state = scrollState, enabled = true), horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text = stringResource(id = R.string.time), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
            Text(text = stringResource(id = R.string.value), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
        }
        when(list){
            null -> {}
            else -> {
                for (item in list){
                    Divider(color = Color.Black, thickness = 1.dp)
                    ListRowItem(booleanEntity = item)
                }
            }
        }
    }
}

@Composable
fun ListRowItem(stringEntity: TextEntity){
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
        , horizontalArrangement = Arrangement.SpaceEvenly){
        Text(text = stringEntity.date.toString(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
        Text(text = stringEntity.value.toString(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)

    }
}

@Composable
fun DaysCheckBox( selected: (bool : Boolean) -> Unit){
    val checkedValue = remember { mutableStateOf(false)}
    Checkbox(checked = checkedValue.value, onCheckedChange = {checkedValue.value = it
    selected(checkedValue.value)})
}

@Composable
fun DaySelection(modifier: Modifier = Modifier,
                 ){

}

@Composable
fun ListRowItem(numericEntity: NumericEntity){
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
        ){
        Text(text = numericEntity.date.toString(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
        Text(text = numericEntity.value.toString(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)

    }
}

@Composable
fun ListRowItem(booleanEntity: BoolEntity){
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
        , horizontalArrangement = Arrangement.SpaceEvenly){
        Text(text = booleanEntity.date.toString(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
        Text(text = booleanEntity.value.toString(), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f), color = Color.White)
    }
}

@Composable
fun ListRowItem(titleEntity: TitleEntity,
                showClicked: (title : String, type : Int) -> Unit,
                newEntryClicked: (title : String, type : Int) -> Unit){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ) {
        val margin = dimensionResource(id = R.dimen.margin16)
        val btn1 = createRef()
        Text(text = titleEntity.title!!, color = Color.White, fontSize = dimensionResource(id = R.dimen.text20).value.sp
        ,modifier = Modifier
                .wrapContentSize()
                .constrainAs(createRef()) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = margin)
                    bottom.linkTo(parent.bottom)
                })
        Button(onClick = { newEntryClicked(titleEntity.title!!, titleEntity.type!!) }, modifier = Modifier.constrainAs(btn1){
            top.linkTo(parent.top)
            end.linkTo(parent.end, margin = margin)
            bottom.linkTo(parent.bottom)
        }, colors = ButtonColor()) {
            Icon(imageVector = Icons.Default.Create, contentDescription = "")
        }

        Button(onClick = { showClicked(titleEntity.title!!, titleEntity.type!!) }, modifier = Modifier.constrainAs(createRef()){
            top.linkTo(parent.top)
            end.linkTo(btn1.start, margin = margin)
            bottom.linkTo(parent.bottom)
        }, colors = ButtonColor()) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        }
    }
}


@Composable
fun ButtonColor(): ButtonColors {
    return ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
}

@Composable
fun TextFieldColor() : TextFieldColors{
    return TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        backgroundColor = Color.White,
        cursorColor = Color.Black,
    )
}

@Composable
fun NumericalGraph(list : List<NumericEntity>?,
                    modifier : Modifier = Modifier){
//todo add x label maybe not :D
    val points = ArrayList<LineChartData.Point>()
    if (list != null) {
        for (item in list){
            points.add(LineChartData.Point(item.value!!, ""))
        }
    }
    if (points.isNotEmpty()){
        LineChart(
            lineChartData = LineChartData(
                points = points.toList()
            ),
            // Optional properties.
            modifier = modifier
                .aspectRatio(1F)
                .padding(16.dp),
            animation = simpleChartAnimation(),
            pointDrawer = FilledCircularPointDrawer(),
            lineDrawer = SolidLineDrawer(),
            xAxisDrawer = SimpleXAxisDrawer(),
            yAxisDrawer = SimpleYAxisDrawer(),
            horizontalOffset = 5f
        )
    }
}

@Composable
fun BooleanPieChart(list : List<BoolEntity>,
                    modifier: Modifier = Modifier){

    var trueVal = 0
    for (item in list){
        if (item.value!!)
        trueVal += 1
    }
    var falseVal = list.size - trueVal

    PieChart(
        pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(falseVal.toFloat(), color = Color.Red),
                PieChartData.Slice(trueVal.toFloat(), color = Color.Green)
            )
        ),
        // Optional properties.
        modifier = modifier
            .aspectRatio(1F)
            .padding(16.dp),
        animation = simpleChartAnimation(),
        sliceDrawer = SimpleSliceDrawer()
    )
}

@Composable
fun DayCheckbox(dayName : String,
                selection: (Boolean) -> Unit){
    val checked = remember { mutableStateOf(false)}
    Column(modifier = Modifier.wrapContentWidth()) {
        Text(text = dayName)
        Checkbox(checked = false, onCheckedChange = {
            checked.value = it
            selection(checked.value)
        })
    }
}

@Composable
fun DaySelector(selection: (List<Boolean>) -> Unit){

    val days = remember{ mutableStateListOf(false, false, false, false, false, false, false)}



}

@Composable
fun SelectTypeView( typeSelected : (type : Int, title : String) -> Unit,
                    modifier: Modifier = Modifier){
    val selected = remember { mutableStateOf(0)}
    val title = remember { mutableStateOf("")}

    val color1 = when(selected.value){
        0->Color.Green
        1->Color.LightGray
        2-> Color.LightGray
        else -> Color.Red
    }

    val color2 = when(selected.value){
        1->Color.Green
        0->Color.LightGray
        2-> Color.LightGray
        else -> Color.Red
    }

    val color3 = when(selected.value){
        2->Color.Green
        1->Color.LightGray
        0-> Color.LightGray
        else -> Color.Red
    }

    Column(modifier = modifier
        .fillMaxWidth(0.8F)
        .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = stringResource(id = R.string.title_of_series),
            fontSize = dimensionResource(id = R.dimen.text20).value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin16)))

        TextField(value = title.value,
            onValueChange = {title.value = it},
            placeholder = { Text(text = stringResource(id =R.string.enter_value), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth() )},
            textStyle = TextStyle(textAlign = TextAlign.Center),
            enabled = true,
            modifier = Modifier
                .height(50.dp)
                .fillMaxSize(0.6F),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldColor()
        )

        Text(text = stringResource(id = R.string.select_input_type),
            fontSize = dimensionResource(id = R.dimen.text20).value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin16)))

        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selected.value = 0
            }
            , shape = RoundedCornerShape(8.dp), backgroundColor = color1) {
            ConstraintLayout() {
                Text(text = stringResource(id = R.string.text), modifier = Modifier.constrainAs(createRef()){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                    fontSize = dimensionResource(id = R.dimen.text20).value.sp)
            }
        }
        
        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin8)))

        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selected.value = 1
            }
            , shape = RoundedCornerShape(8.dp), backgroundColor = color2) {
            ConstraintLayout() {
                Text(text = stringResource(id = R.string.numeric),
                    modifier = Modifier.constrainAs(createRef()){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                fontSize = dimensionResource(id = R.dimen.text20).value.sp)
            }
        }

        Spacer(Modifier.size(dimensionResource(id = R.dimen.margin8)))

        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selected.value = 2
            }
            , shape = RoundedCornerShape(8.dp), backgroundColor = color3) {
            ConstraintLayout() {
                Text(text = stringResource(id = R.string.bool), modifier = Modifier.constrainAs(createRef()){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                    fontSize = dimensionResource(id = R.dimen.text20).value.sp)
            }
        }

        Row(modifier = Modifier.size(100.dp)){
            CardButton(text = stringResource(id = R.string.next),
                backgroundColor = R.color.yellow,
                textColor = R.color.black,
                textSize = dimensionResource(id = R.dimen.text16).value.toInt(),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.margin16))) {
                typeSelected(selected.value, title.value)
            }
        }
    }
}


@Composable
fun fillAllButton(modifier: Modifier = Modifier
    ,buttonClicked : () -> Unit){
    Button(
        modifier = modifier
            .fillMaxWidth(0.8F)
            .height(80.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.margin8)),
        colors = ButtonColor(),
        onClick = { buttonClicked },
    ){
        Text(text = stringResource(id = R.string.fill_all), color = Color.Black, fontSize = dimensionResource(
            id = R.dimen.text32
        ).value.sp
        )
    }

}

//////////////PREVIEW/////////////

@Preview
@Composable
fun previewButton(){
    ConstraintLayout(modifier = Modifier.size(200.dp)) {
        CardButton( modifier = Modifier.fillMaxSize(),
            text = "New Series",
            backgroundColor = R.color.teal_200,
            textColor = R.color.white,
            textSize = 20,
            clicked = {})
    }
}


@Preview
@Composable
fun  previewAds(){
    adsLayout(modifier = Modifier.wrapContentSize(), closeAds = {})
}

@Preview
@Composable
fun previewTextInput(){

        stringInput(okButtonClicked = {})

}

@Preview
@Composable
fun previewNumberInput(){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        numericInput(okButtonClicked = {})
    }
}

@Preview
@Composable
fun previewBoolInput(){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        booleanInput(okButtonClicked = {})
    }
}


@Preview
@Composable
fun stringData(){
    val stringList = arrayOf(TextEntity(date = Date(50000).toString(), title = "", value = "a", uid = 0),
        TextEntity(date = Date(50000).toString(), title = "", value = "a", uid = 0),
        TextEntity(date = Date(50000).toString(), title = "", value = "a", uid = 0),
        TextEntity(date = Date(50000).toString(), title = "", value = "a", uid = 0)
    )

    StringList(list = stringList.toMutableList())
}

//@Preview
//@Composable
//fun floatData(){
//    val stringList = arrayOf(FloatData(date = Date(50000), 1F),
//        FloatData(date = Date(50000), 2F),
//        FloatData(date = Date(50000), 3F),
//        FloatData(date = Date(50000), 4F)
//    )
//
//    FloatList(list = stringList.toList())
//}

//@Preview
//@Composable
//fun booleanData(){
//    val stringList = arrayOf(BooleanData(date = Date(50000), false),
//        BooleanData(date = Date(50000), true),
//        BooleanData(date = Date(50000), false),
//        BooleanData(date = Date(50000), false)
//    )
//
//    BooleanList(list = stringList.toList())
//}

@Preview
@Composable
fun previewPieChart(){
    val stringList = arrayOf(BooleanData(date = Date(50000), false),
        BooleanData(date = Date(50000), true),
        BooleanData(date = Date(50000), false),
        BooleanData(date = Date(50000), false)
    )

//    BooleanPieChart(list = stringList.toMutableList())
}

@Preview
@Composable
fun previewLineData(){
    val stringList = arrayOf(FloatData(date = Date(50000), 1F),
        FloatData(date = Date(50000), 2F),
        FloatData(date = Date(50000), 3F),
        FloatData(date = Date(50000), 5F)
    )
//    NumericalGraph(list = stringList.toMutableList())
}

@Preview
@Composable
fun previewTypeSelect() = ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    SelectTypeView(typeSelected = {_,_ -> })
}

@Preview
@Composable
fun previewFillAllButton() = fillAllButton {

}

@Preview
@Composable
fun previewSingleCheckbox(){
    ConstraintLayout(modifier = Modifier.wrapContentWidth()) {
        DayCheckbox(dayName = "m", selection = {})
    }
}