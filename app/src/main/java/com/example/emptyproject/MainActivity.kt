package com.example.emptyproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            EmptyProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString1 {
            withStyle(style = ParagraphStyle(lineHeight = 2.5.em,
                                             textAlign = TextAlign.Center)) {
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1FBC86),
                                            fontSize = 50.sp,
                                            shadow = Shadow(
                                                color = Color(0xFFC80EEF),
                                                offset = Offset(10f, 10f),
                                                blurRadius = 15f
                                            ))) {
                    append("Hello $name!\n")
                }
                withStyle(
                    style = SpanStyle(color = Color(0xFFFCBA43),
                                      fontStyle = FontStyle.Italic,
                                      fontSize = 40.sp,
                                      shadow = Shadow(
                                          color = Color(0xFF0e2dcf),
                                          offset = Offset(15f, 15f),
                                          blurRadius = 15f
                                      ))) {
                    append("I'm Android\n")
                }
                append("playing with styles")
            }
        },
        modifier = modifier,
        fontSize = 25.sp,
    )
}

@Preview(
    device = "spec:parent=pixel_5",
    showBackground = true, showSystemUi = true
)
@Composable
fun GreetingPreview() {
    EmptyProjectTheme {
        Column{
            Spacer(Modifier.height(200.dp))
            Greeting("World")
            Spacer(Modifier.height(200.dp))
        }
    }
}