package com.example.emptyproject

import android.util.Log

interface FifteenEngine {
    fun transitionState(oldState: List<Int>, cell: Int): List<Int>
    fun isWin(state: List<Int>): Boolean
    fun getInitialState(): List<Int>
    fun isStepPossible(inputList: List<Int>, numberForMove: Int): Boolean
    fun isCorrectPosition(numbers: List<Int>, number: Int): Boolean

    companion object: FifteenEngine {
        val FINAL_STATE =  List(16){ it + 1 }

        override fun getInitialState(): List<Int> {
            var playingChips: List<Int>
            do {
                playingChips = List(16){ it + 1 }.toMutableList().shuffled()
            } while (!isSolvable(playingChips))
            return playingChips
        }

//

        private fun isSolvable(cells: List<Int>, size: Int = 4): Boolean {
            val inversions = cells
                .filter { it != 16 }
                .flatMapIndexed { index, value ->
                    cells.subList(index + 1, cells.size)
                        .filter { it != 16 && it < value }
                        .map { 1 } // Count one inversion
                }
                .sum()

            val emptyRow =
                (size - cells.indexOf(16) / size) // строка с пустой клеткой (отсчет снизу)

            return if (size % 2 == 0) {
                (emptyRow % 2 == 0) != (inversions % 2 == 0)
            } else {
                inversions % 2 == 0
            }
        }

        override fun transitionState(oldState: List<Int>, cell: Int): List<Int> {
            if(isStepPossible(oldState, cell)) {
                val indexFromMove = oldState.indexOf(cell)
                val indexToMove = oldState.indexOf(16)
                val newState = oldState.toMutableList()
                newState[indexFromMove] = newState[indexToMove].also { newState[indexToMove] = newState[indexFromMove]}
                return newState.toList()
            } else {
                return oldState
            }

        }

        override fun isWin(playingChips: List<Int> ): Boolean {
            return playingChips == FINAL_STATE
        }

        override fun isStepPossible(inputList: List<Int>, numberForMove: Int): Boolean {
            val indexToMove = inputList.indexOf(numberForMove)
            val emptyPlaceIndex = inputList.indexOf(16)
            val neighbors = listOf(
                emptyPlaceIndex - 4,
                emptyPlaceIndex + 4,
                emptyPlaceIndex - 1,
                emptyPlaceIndex + 1
            ).filter { it in inputList.indices }
            return indexToMove in neighbors

        }

        override fun isCorrectPosition(numbers: List<Int>, number: Int): Boolean {
            Log.i("Chip", "checking $number on ${numbers[number]} position")
            return number == numbers[number - 1]

        }

    }
}