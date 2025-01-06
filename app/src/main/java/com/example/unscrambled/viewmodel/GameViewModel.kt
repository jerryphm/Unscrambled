package com.example.unscrambled.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unscrambled.data.GameUiState
import com.example.unscrambled.data.Vocab
import com.example.unscrambled.data.vocabData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val SCORE_INCREASED_EACH_TIME = 10
const val MAX_WORD_PER_GAME = 10
const val TOTAL_SCORE = SCORE_INCREASED_EACH_TIME * MAX_WORD_PER_GAME

class GameViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess: MutableState<String> = mutableStateOf("")
    var usedWords: MutableSet<String> = mutableSetOf()

    fun pickNewRandomVocab(): Vocab {
        lateinit var randomVocab: Vocab
        do {
            randomVocab = vocabData.random()
        } while (usedWords.contains(randomVocab.unscrambledWord))
        return randomVocab
    }

    fun shuffleWord(word: String): String {
        lateinit var scrambledWord: String
        do {
            val tempArr = word.toCharArray()
            tempArr.shuffle()
            scrambledWord = tempArr.joinToString("")
        } while(scrambledWord == word)
        return scrambledWord
    }

    fun startGame() {
        // clear previous game state
        userGuess.value = ""
        usedWords.clear()
        _uiState.update { currentState ->
            currentState.copy(
                unscrambledWord = "",
                scrambledWord = "",
                wordDefinition = "",
                score = 0,
                wordCount = 1,
                isError = false,
                isOver = false
            )
        }

        // setup new game state
        val (unscrambledWord, wordDefinition) = pickNewRandomVocab()
        val scrambledWord = shuffleWord(unscrambledWord)
        usedWords.add(unscrambledWord)
        _uiState.update { currentState ->
            currentState.copy(
                unscrambledWord = unscrambledWord,
                scrambledWord = scrambledWord,
                wordDefinition = wordDefinition,
            )
        }
    }

    fun updateUserGuess(guess: String) {
        _uiState.update { currentState ->
            currentState.copy(isError = false)
        }
        userGuess.value = guess
    }

    fun check() {
        if (userGuess.value == uiState.value.unscrambledWord) {
            if (_uiState.value.wordCount == MAX_WORD_PER_GAME) {
                _uiState.update { s ->
                    s.copy(
                        score = s.score.plus(SCORE_INCREASED_EACH_TIME),
                        isOver = true
                    )
                }
            } else {
                val (unscrambledWord, wordDefinition) = pickNewRandomVocab()
                val scrambledWord = shuffleWord(unscrambledWord)
                usedWords.add(unscrambledWord)
                userGuess.value = ""
                _uiState.update { currentState ->
                    currentState.copy(
                        score = currentState.score.plus(SCORE_INCREASED_EACH_TIME),
                        wordCount = currentState.wordCount.plus(1),
                        isError = false,
                        unscrambledWord = unscrambledWord,
                        scrambledWord = scrambledWord,
                        wordDefinition = wordDefinition,
                    )
                }
            }
        } else {
            userGuess.value = ""
            _uiState.update { currentState ->
                currentState.copy(isError = true)
            }
        }
    }

    fun skip() {
        if (_uiState.value.wordCount == MAX_WORD_PER_GAME) {
            _uiState.update { s ->
                s.copy(isOver = true)
            }
        } else {
            val (unscrambledWord, wordDefinition) = pickNewRandomVocab()
            val scrambledWord = shuffleWord(unscrambledWord)
            usedWords.add(unscrambledWord)
            userGuess.value = ""
            _uiState.update { currentState ->
                currentState.copy(
                    wordCount = currentState.wordCount.plus(1),
                    isError = false,
                    unscrambledWord = unscrambledWord,
                    scrambledWord = scrambledWord,
                    wordDefinition = wordDefinition,
                )
            }
        }
    }

    fun replay() {
        startGame()
    }

    init {
        startGame()
    }
}