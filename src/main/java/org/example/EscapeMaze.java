package org.example;

import java.util.*;

public class EscapeMaze {

    enum Direction {
        DOWN,UP,LEFT,RIGHT
    }

    private final String Forward = "F";
    private final String TurnLeft = "L";
    private final String TurnRight = "R";
    private final String TurnBack = "B";
    class Cords {
        int x;
        int y;
        Direction position;

        public Cords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Cords() {
        }

        @Override
        public String toString() {
            return "Cords{" +
                    "x=" + x +
                    ", y=" + y +
                    ", position=" + position +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cords cords)) return false;
            return x == cords.x && y == cords.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    private char[][] maze;
    private Cords currentPosition;
    private int xLength;
    private int yLength;
    public EscapeMaze(char[][] maze) {
       this.maze = maze;
       xLength = maze.length;
       yLength = maze[0].length;
       currentPosition = this.findStartingCoordinates(maze);
        System.out.println(currentPosition);
    }

    public String escape() {

        int counter = 0;
        while(counter < 4) {

        }
        return "";
    }

    private Cords play(List<Cords> options) {
        var current = options.get(0);
        this.currentPosition.x = current.x;
        this.currentPosition.y = current.y;
        System.out.println("Playing " + currentPosition);
        return current;
    }
    private List<Cords> options(Map<Cords, Boolean> previousMoves) {

        List<Cords> available = new ArrayList<>();
        var c = currentPosition;
        Cords leftCords = new Cords(c.x, c.y - 1);
        Cords rightCords = new Cords(c.x, c.y + 1);
        Cords upCords = new Cords(c.x - 1, c.y);
        Cords downCords = new Cords(c.x + 1, c.y);

        if (canMove(leftCords) && !previousMoves.containsKey(leftCords)) {
            leftCords.position = Direction.LEFT;
            available.add(leftCords);
        }
        if (canMove(rightCords) && !previousMoves.containsKey(rightCords)) {
            leftCords.position = Direction.RIGHT;
            available.add(rightCords);
        }
        if (canMove(upCords) && !previousMoves.containsKey(upCords)) {
            leftCords.position = Direction.DOWN;
            available.add(upCords);
        }
        if (canMove(downCords) && !previousMoves.containsKey(downCords)) {
            leftCords.position = Direction.UP;
            available.add(downCords);
        }
        return available;
    }

    public void move(Direction dir) {

    }

    public void turn() {

    }

    public boolean canMove(Cords cords) {
        return maze[cords.x][cords.y] == ' ';
    }
    public boolean canMoveLeft() {
        return maze[currentPosition.x][currentPosition.y - 1] == ' ';
    }
    public boolean canMoveRight() {
        return maze[currentPosition.x][currentPosition.y + 1] == ' ';
    }
    public boolean canMoveDown() {
        return maze[currentPosition.x + 1][currentPosition.y ] == ' ';
    }
    public boolean canMoveUp() {
        return maze[currentPosition.x - 1][currentPosition.y] == ' ';
    }
    public boolean hasEscapedMaze() {
        return currentPosition.x == 0 || currentPosition.x == xLength - 1 || currentPosition.y == 0 || currentPosition.y == yLength - 1;
    }

    private Cords findStartingCoordinates(char[][] maze) {

        var cords = Set.of('<','>','^','v');
        var c = new Cords();
        for (var i = 0; i < xLength; i++) {
            for (var j = 0; j < yLength; j++) {
                var current = maze[i][j];
                if (cords.contains(current)) {
                    c.x = i;
                    c.y = j;

                    switch (current) {
                        case '<' -> c.position = Direction.LEFT;
                        case '>' -> c.position = Direction.RIGHT;
                        case '^' -> c.position = Direction.UP;
                        case 'v' -> c.position = Direction.DOWN;
                    }
                   return c;
                }
            }
        }
        throw new IllegalArgumentException("Starting position not found.");
    }
}
