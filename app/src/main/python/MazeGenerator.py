import numpy as np
import random

def generate_maze(width, height):
    # Initialize the maze grid with walls (1)
    maze = np.ones((height, width), dtype=np.int8)

    # Directions to move in the grid
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    def is_valid_cell(x, y):
        return 0 <= x < height and 0 <= y < width

    def has_unvisited_neighbors(x, y):
        for dx, dy in directions:
            nx, ny = x + dx * 2, y + dy * 2
            if is_valid_cell(nx, ny) and maze[nx][ny] == 1:
                return True
        return False

    def remove_wall(x, y, nx, ny):
        maze[(x + nx) // 2][(y + ny) // 2] = 0

    def recursive_backtracker(x, y):
        maze[x][y] = 0
        random.shuffle(directions)
        for dx, dy in directions:
            nx, ny = x + dx * 2, y + dy * 2
            if is_valid_cell(nx, ny) and maze[nx][ny] == 1 and has_unvisited_neighbors(nx, ny):
                remove_wall(x, y, nx, ny)
                recursive_backtracker(nx, ny)

    # Start the maze from a random cell
    start_x, start_y = random.randrange(0, height, 2), random.randrange(0, width, 2)
    recursive_backtracker(start_x, start_y)

    # Set start and finish points
    maze[start_x][start_y] = 2
    finish_x, finish_y = start_x, start_y
    while maze[finish_x][finish_y] != 0:
        finish_x, finish_y = random.randrange(0, height), random.randrange(0, width)
    maze[finish_x][finish_y] = 3

    return maze

