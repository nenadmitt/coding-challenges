package org.example.escape_maze;

import org.example.EscapeMaze;

import java.util.Set;

public class EscapeMazeGame {

    private char[][] matrix;

    public EscapeMazeGame(char[][] matrix) {
        this.matrix = matrix;
    }

    public char[][] getMatrix() {
        return matrix;
    }
}
