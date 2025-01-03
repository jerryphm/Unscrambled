package com.example.unscrambled

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unscrambled.ui.theme.Black
import com.example.unscrambled.ui.theme.Blue
import com.example.unscrambled.ui.theme.Gray
import com.example.unscrambled.ui.theme.Green
import com.example.unscrambled.ui.theme.Pink
import com.example.unscrambled.ui.theme.UnscrambledTheme
import com.example.unscrambled.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnscrambledTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {
    Scaffold(
        containerColor = Blue,
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            GameStatus()
            CurrentScrambledWord()
            UserGuess()
            CheckOrSkipButton()
        }
    }
}

@Composable
fun GameStatus(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(R.drawable.coin), contentDescription = "score")
            Spacer(Modifier.width(4.dp))
            Text("120", style = MaterialTheme.typography.displaySmall)
        }
        Text("3/10", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun CurrentScrambledWord(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(140.dp))
        Text(
            "Serup",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "very good or pleasant, excellent.very good or pleasant, excellent.very good or pleasant, excellent.very good or pleasant, excellent.",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )
    }
}

@Composable
fun UserGuess(modifier: Modifier = Modifier) {
    Spacer(Modifier.height(54.dp))
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Type your answer...", style = MaterialTheme.typography.titleMedium, color = Gray) },
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = White,
            unfocusedContainerColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        textStyle = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CheckOrSkipButton(modifier: Modifier = Modifier) {
    Spacer(Modifier.height(32.dp))
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {},
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Green,
                contentColor = Black
            ),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
        ) {
            Text("Skip", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.width(12.dp))
        Button(
            onClick = {},
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink,
                contentColor = Black
            ),
            contentPadding = PaddingValues(horizontal = 64.dp, vertical = 12.dp),
        ) {
            Text("Check!", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambledTheme {
        GameScreen()
    }
}