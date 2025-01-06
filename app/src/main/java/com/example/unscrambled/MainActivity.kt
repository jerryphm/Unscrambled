package com.example.unscrambled

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscrambled.ui.theme.Black
import com.example.unscrambled.ui.theme.Blue
import com.example.unscrambled.ui.theme.Gray
import com.example.unscrambled.ui.theme.Green
import com.example.unscrambled.ui.theme.Pink
import com.example.unscrambled.ui.theme.UnscrambledTheme
import com.example.unscrambled.ui.theme.White
import com.example.unscrambled.viewmodel.GameViewModel
import com.example.unscrambled.viewmodel.MAX_WORD_PER_GAME
import com.example.unscrambled.viewmodel.SCORE_INCREASED_EACH_TIME
import com.example.unscrambled.viewmodel.TOTAL_SCORE

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
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()

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
            if (gameUiState.isOver) {
                GameOver(score = gameUiState.score, replay = { gameViewModel.replay() })
            } else {
                Spacer(Modifier.height(8.dp))
                GameStatus(
                    score = gameUiState.score,
                    wordCount = gameUiState.wordCount
                )
                CurrentScrambledWord(
                    scrambledWord = gameUiState.scrambledWord,
                    wordDefinition = gameUiState.wordDefinition
                )
                UserGuess(
                    isError = gameUiState.isError,
                    userGuess = gameViewModel.userGuess.value,
                    updateUserGuess = {str -> gameViewModel.updateUserGuess(str)}
                )
                CheckOrSkipButton(
                    check = { gameViewModel.check() },
                    skip = { gameViewModel.skip() }
                )
            }

        }
    }
}

@Composable
fun GameOver(
    score: Int,
    replay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        val msg = when {
            score <= TOTAL_SCORE / 3 -> "Keep trying! You can do better. Your score: "
            score > TOTAL_SCORE / 3 && score <= TOTAL_SCORE / 3 * 2 -> "Good job! You're almost there. Your score: "
            else -> "Excellent work! You nailed it. Your score: "
        }
        Text(
            buildAnnotatedString {
                append(msg)
                withStyle(style = SpanStyle(color = Color(0xFFF0AA89))) {
                    append(score.toString())
                }
            },
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = replay,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink,
                contentColor = Black
            ),
            contentPadding = PaddingValues(horizontal = 64.dp, vertical = 12.dp),
        ) {
            Text("Replay", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun GameStatus(score: Int, wordCount: Int, modifier: Modifier = Modifier) {
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
            Text(score.toString(), style = MaterialTheme.typography.displaySmall)
        }
        Text("$wordCount/$MAX_WORD_PER_GAME", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun CurrentScrambledWord(
    scrambledWord: String,
    wordDefinition: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(140.dp))
        Text(
            scrambledWord,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(4.dp))
        Text(
            wordDefinition,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )
    }
}

@Composable
fun UserGuess(
    isError: Boolean,
    userGuess: String,
    updateUserGuess: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(Modifier.height(54.dp))
    TextField(
        value = userGuess,
        onValueChange = updateUserGuess,
        placeholder = { Text("Type your answer...", style = MaterialTheme.typography.titleMedium, color = Gray) },
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = White,
            unfocusedContainerColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            errorContainerColor = White,
            errorCursorColor = Black,
            cursorColor = Black
        ),
        textStyle = MaterialTheme.typography.titleMedium,
        isError = isError,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = if (isError) Color.Red else Color.Transparent, shape = CircleShape)
    )
}

@Composable
fun CheckOrSkipButton(
    check: () -> Unit,
    skip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(Modifier.height(32.dp))
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = skip,
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
            onClick = check,
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