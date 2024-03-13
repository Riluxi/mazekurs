package com.example.diplomamazegame.ui.maze

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chaquo.python.Python
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MazeViewModel @Inject constructor() : ViewModel() {

    private var playerGridPosition = mutableStateOf( 1 to 0)
    val playerPosition: State<Pair<Int, Int>> = playerGridPosition

    private var _mazeState = mutableStateOf(generatePythonMaze())
    val mazeState: State<Array<Array<Int>>> = _mazeState


    fun movePlayer(deltaX: Int, deltaY: Int, maze: Array<Array<Int>>) {
        val (currentX, currentY) = playerGridPosition.value
        val nextX = (currentX + deltaX).coerceIn(0, maze[0].size - 1)
        val nextY = (currentY + deltaY).coerceIn(0, maze.size - 1)

        // Move player if the next cell is not a wall
        if (maze[nextY][nextX] != 1) {
            playerGridPosition.value = Pair(nextX, nextY)
        }
    }

    private fun generatePythonMaze() : Array<Array<Int>>  {
        val py = Python.getInstance()
        val module = py.getModule( "MazeGenerator" )
        val numsLength = module[ "generate_maze" ]
        val res = numsLength?.call(20, 20).toString()
            .trimIndent()

        val rows = res.lines() // Splitting by lines

        val result = rows.map { row ->
            row.trim('[', ']') // Removing the brackets
                .split(" ") // Splitting by spaces
                .mapNotNull { it.toIntOrNull() } // Converting to Integers, ignoring errors
                .toTypedArray()
        }.toTypedArray()

        Timber.d(res.toString())

        val pos = findIndexesOfNumber(result, 2)
        pos.firstOrNull()?.let {
            playerGridPosition.value = it
        }

        return result
    }

    fun findIndexesOfNumber(array: Array<Array<Int>>, number: Int): List<Pair<Int, Int>> {
        val indexes = mutableListOf<Pair<Int, Int>>()

        array.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, value ->
                if (value == number) {
                    indexes.add(Pair(columnIndex, rowIndex))
                }
            }
        }

        return indexes
    }
}
