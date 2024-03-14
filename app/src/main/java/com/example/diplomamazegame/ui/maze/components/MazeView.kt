package com.example.diplomamazegame.ui.maze.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MazeView(maze: Array<Array<Int>>, playerGridPosition: Pair<Int, Int>) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val cellSize = size.width / maze[0].size
        maze.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, cell ->
                val color = when (cell) {
                    1 -> Color.Black
                    2 -> Color.Green
                    3 -> Color.Yellow
                    else -> Color.White
                }
                drawRect(
                    color = color,
                    topLeft = Offset(columnIndex * cellSize, rowIndex * cellSize),
                    size = Size(cellSize, cellSize)
                )
            }
        }

        val (playerX, playerY) = playerGridPosition

        drawCircle(
            color = Color.Red,
            center = Offset(playerX * cellSize + cellSize / 2, playerY * cellSize + cellSize / 2),
            radius = cellSize / 4
        )
    }
}