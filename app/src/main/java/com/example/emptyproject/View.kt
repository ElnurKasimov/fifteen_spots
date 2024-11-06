package com.example.emptyproject

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyproject.ui.theme.EmptyProjectTheme


@Composable
fun Grid(
    cells: List<Int>,
    modifier: Modifier,
    onChipClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        repeat(4) { outerIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                repeat(4) { innerIndex ->
                    Chip(cells[outerIndex * 4 + innerIndex], onChipClick)
                }
            }
        }
    }
}


@Composable
fun Chip(
    cell: Int,
    onClick: (Int) -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    val myBorder: BorderStroke?
    val myText: String
    val myColor: Long
    if (cell == 16) {
        myBorder = null
        myText = ""
        myColor = 0x00FEE1FC
    } else {
        myBorder = BorderStroke(
            5.dp, Brush.linearGradient(
                listOf(
                    Color(0xFFE5DBE4),
                    Color(0xFF71566E)
                )
            )
        )
        myText = cell.toString()
        myColor = 0xFFFEE1FC
    }
    Button(
        onClick = {
            onClick(cell)
            Log.i("MyButtonOnClick", "Clicking on chip with number $cell")
        },
        modifier = Modifier.size(80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(myColor),
            contentColor = Color(0xFFFE5A8F)
        ),
        border = myBorder,
        shape = shape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = myText,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    device = "spec:parent=pixel_5",
    showBackground = true, showSystemUi = true, locale = "uk"
)
@Composable
fun GreetingPreview() {
    val engine = object : FifteenEngine by FifteenEngine.Companion {
        override fun getInitialState(): List<Int> =
            buildList {
                repeat(14) {
                    add(it + 1)
                }
                add(16)
                add(15)
            }
    }
    var cells by remember { mutableStateOf(engine.getInitialState()) }

    EmptyProjectTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.fifteen_spots_game),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFE5A8F)
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.padding(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    containerColor = Color.Transparent
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                cells = engine.getInitialState()
                            },
                            modifier = Modifier
                                .width(250.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFEE1FC),
                                contentColor = Color(0xFFFE5A8F),
                            ),
                            shape = ShapeDefaults.Medium,
                            border = BorderStroke(
                                width = 4.dp ,
                                color = Color(0x9971566E)
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.reset),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Main(cells,
                onCellsUpdate = { newState -> cells = newState },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                engine = engine
            )
        }
    }
}