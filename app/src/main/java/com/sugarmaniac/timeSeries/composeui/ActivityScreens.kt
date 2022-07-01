@file:JvmName("ScreensKt")

package com.sugarmaniac.timeSeries.composeui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.sugarmaniac.timeSeries.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    newSeriesClicked:  () -> Unit,
    newEntryClicked: () -> Unit,
    settingClicked: () -> Unit
){
    val bottomSheetScaffoldState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(sheetState = bottomSheetScaffoldState,
        sheetContent = {Text("")},
    sheetBackgroundColor = Color.DarkGray) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (newButton, entryButton) = createRefs()
            val marginLarge = dimensionResource(id = R.dimen.margin32)

            Button(onClick = { settingClicked() }, modifier = Modifier.size(dimensionResource(id = R.dimen.button40)).constrainAs(createRef()){
                top.linkTo(parent.top, margin = marginLarge)
                end.linkTo(parent.end, margin = marginLarge)
            }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
            }

            CardButton(modifier = Modifier
                .fillMaxWidth(0.4F)
                .constrainAs(newButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(entryButton.start)
                    bottom.linkTo(parent.bottom)
                },
                text = stringResource(id = R.string.new_serie),
                backgroundColor =  R.color.teal_200,
                textColor = R.color.white,
                textSize = dimensionResource(id = R.dimen.menu_text_size).value.toInt(),
                clicked = newSeriesClicked)


            CardButton(modifier = Modifier
                .fillMaxWidth(0.4F)
                .constrainAs(entryButton) {
                    top.linkTo(parent.top)
                    start.linkTo(newButton.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                text = stringResource(id = R.string.current_series),
                backgroundColor =  R.color.teal_200,
                textColor = R.color.white,
                textSize = dimensionResource(id = R.dimen.menu_text_size).value.toInt(),
                clicked = newEntryClicked
            )

//            adsView(modifier = Modifier.constrainAs(createRef()){
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//                bottom.linkTo(parent.bottom)
//            })
        }
    }
}

@Composable
fun NewSeriesScreen(typeSelected : (type : Int, title : String) -> Unit){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val stv = createRef()
        val margin = dimensionResource(id = R.dimen.margin32)
        SelectTypeView(modifier = Modifier.constrainAs(stv){
            top.linkTo(parent.top, margin = margin)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
        }, typeSelected = typeSelected)





    }
}



@Preview
@Composable
fun mainScreen(){
    MainScreen(newEntryClicked = {}, newSeriesClicked = {}, settingClicked =  {})
}