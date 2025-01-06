package com.example.unscrambled.data

data class GameUiState(
    val unscrambledWord: String = "",
    val scrambledWord: String = "",
    val wordDefinition: String = "",
    val score: Int = 0,
    val wordCount: Int = 1,
    val isError: Boolean = false,
    val isOver: Boolean = false
)