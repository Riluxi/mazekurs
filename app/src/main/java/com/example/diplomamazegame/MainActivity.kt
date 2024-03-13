package com.example.diplomamazegame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplomamazegame.ui.maze.MazeViewModel
import com.example.diplomamazegame.ui.theme.DiplomaMazeGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            DiplomaMazeGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MazeGame()
                }
            }
        }
    }
}


@Composable
fun MazeGame() {
    val viewModel: MazeViewModel = hiltViewModel()
    val playerGridPosition by viewModel.playerPosition
    val maze by viewModel.mazeState

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MazeView(maze = maze, playerGridPosition = playerGridPosition)
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { viewModel.movePlayer(-1, 0, maze) }) { Text("Left") }
            Button(onClick = { viewModel.movePlayer(1, 0, maze) }) { Text("Right") }
            Button(onClick = { viewModel.movePlayer(0, -1, maze) }) { Text("Up") }
            Button(onClick = { viewModel.movePlayer(0, 1, maze) }) { Text("Down") }
        }
    }
}


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