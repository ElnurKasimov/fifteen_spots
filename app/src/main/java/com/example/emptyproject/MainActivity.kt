package com.example.emptyproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.emptyproject.ui.theme.EmptyProjectTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.buildAnnotatedString as buildAnnotatedString1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmptyProjectTheme { StateHolder() }
        }
    }
}

@Composable
fun StateHolder(engine: FifteenEngine = FifteenEngine) {
    var cells by remember { mutableStateOf(engine.getInitialState()) }
    val isWin by remember { derivedStateOf {engine.isWin(cells)} }
    var move by remember { mutableIntStateOf(0) }
    var startTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    fun onChipClick(chipNumber : Int) {
        val oldState = cells
        cells = engine.transitionState(cells, chipNumber)
        if(cells != oldState) {
            move++
        }
    }

    fun onResetClick() {
        cells = engine.getInitialState()
        startTime = System.currentTimeMillis()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopBar() },
        bottomBar = { MyBottomBar(::onResetClick) }
    ) { innerPadding ->
        Main(
            cells,
            isWin,
            move,
            startTime,
            onChipClick = ::onChipClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
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
}

@Composable
fun MyBottomBar(onResetClick: () -> Unit) {
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
                onClick = onResetClick,
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

@Composable
fun Main(
    cells: List<Int>,
    isWin: Boolean,
    move: Int,
    startTime: Long,
    onChipClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
//    engine: FifteenEngine = FifteenEngine
) {

    if (!isWin) {
        Grid(
            cells,
            modifier,
            onChipClick
        )
    } else {
        val endTime = System.currentTimeMillis()
        val timeSpent = (endTime - startTime) / 1000
        val durationMin = timeSpent / 60
        val durationSec = timeSpent % 60
        val text1 = stringResource(R.string.victory)
        val text2 = stringResource(R.string.you_have_won_for_min_and_sec, durationMin, durationSec)
        val text3 = stringResource(R.string.performed_moves, move)
        Text(
            text = buildAnnotatedString1 {
                withStyle(
                    style = ParagraphStyle(
                        lineHeight = 3.5.em,
                        textAlign = TextAlign.Center
                    )
                ) {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFf4fb04),
                            fontSize = 60.sp,
                            shadow = Shadow(
                                color = Color(0xFF0e2dcf),
                                offset = Offset(10f, 10f),
                                blurRadius = 15f
                            )
                        )
                    ) {
                        append(text1)
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0x770e2dcf),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 36.sp
                        )
                    ) {
                        append(text2)
                        append(text3)
                    }
                }
            },
            modifier = modifier.wrapContentSize(Alignment.Center)
        )
    }

}



