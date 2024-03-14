package com.example.diplomamazegame.ui.maze

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplomamazegame.ui.maze.components.MazeView

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
