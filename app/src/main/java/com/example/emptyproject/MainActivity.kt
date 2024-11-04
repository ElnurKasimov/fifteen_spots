
package com.example.emptyproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.emptyproject.ui.theme.EmptyProjectTheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.buildAnnotatedString as buildAnnotatedString1

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmptyProjectTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = stringResource(R.string.fifteen_spots_game),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFE5A8F)
                                    )
                            }
                        )
                    }
                ) { innerPadding ->
                    Main(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding))
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = buildAnnotatedString1 {
//            withStyle(style = ParagraphStyle(lineHeight = 2.5.em,
//                                             textAlign = TextAlign.Center)) {
//                withStyle(
//                    style = SpanStyle(fontWeight = FontWeight.Bold,
//                                            color = Color(0xFF1FBC86),
//                                            fontSize = 50.sp,
//                                            shadow = Shadow(
//                                                color = Color(0xFFC80EEF),
//                                                offset = Offset(10f, 10f),
//                                                blurRadius = 15f
//                                            ))) {
//                    append("Hello $name!\n")
//                }
//                withStyle(
//                    style = SpanStyle(color = Color(0xFFFCBA43),
//                                      fontStyle = FontStyle.Italic,
//                                      fontSize = 40.sp,
//                                      shadow = Shadow(
//                                          color = Color(0xFF0e2dcf),
//                                          offset = Offset(15f, 15f),
//                                          blurRadius = 15f
//                                      ))) {
//                    append("I'm Android\n")
//                }
//                append("playing with styles")
//            }
//        },
//        modifier = modifier,
//        fontSize = 25.sp,
//    )
//    Box(Modifier){
//
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    device = "spec:parent=pixel_5",
    showBackground = true, showSystemUi = true, locale = "uk"
)
@Composable
fun GreetingPreview() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.fifteen_spots_game),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFE5A8F)
                    )
                }
            )
        }
    ) { innerPadding ->
        Main(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding))
    }
}

@Composable
fun Main (
    modifier: Modifier = Modifier,
    engine: FifteenEngine = FifteenEngine) {
    var cells by remember {
        mutableStateOf(engine.getInitialState())
    }
    Grid(
        cells,
        modifier
    ) {
            chipNumber -> cells = engine.transitionState(cells, chipNumber)
    }
}

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
                repeat(4) {innerIndex ->
                    Chip(cells[outerIndex * 4 + innerIndex], onChipClick )
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
    if(cell == 16) {
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