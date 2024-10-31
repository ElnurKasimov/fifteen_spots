package com.example.emptyproject

interface FifteenEngine {
    fun transitionState(oldState: List<Int>, cell: Int): List<Int>
    fun isWin(state: List<Int>): Boolean
    fun getInitialState(): List<Int>
    fun isStepPossible(inputList: List<Int>, numberForMove: Int): Boolean
}