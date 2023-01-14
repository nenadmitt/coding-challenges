package org.example;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Maze {

    static class Point {

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point point)) return false;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private final char[][] maze;
    private final int mazeX;
    private final int mazeY;
    private final Point startingPosition;

    public Maze(String[] mazeString) {
        maze = convertToMatrix(mazeString);
        mazeX = maze[0].length;
        mazeY = maze.length;
        startingPosition = getPlayerPosition();
    }

    public static boolean hasExit(String[] maze) {
        return new Maze(maze).canExit();
    }

    private char[][] convertToMatrix(String[] maze) {
        char[][] matrix = new char[maze.length][maze[0].length()];
        var expectedMazeLength = maze[0].length();

        for (var i = 0; i < matrix.length; i++) {
            // Convert maze to a proper rectangle
            StringBuilder currentRow = new StringBuilder(maze[i]);
            if (currentRow.length() < expectedMazeLength) {
                for (int k = 0; k <= expectedMazeLength - currentRow.length(); k++) {
                    currentRow.append('#');
                }
            }
            for (var j = 0; j < expectedMazeLength; j++) {
                matrix[i][j] = currentRow.charAt(j);
            }
        }
        return matrix;
    }

    public boolean canExit() {

        Map<Point, Boolean> playedPositions = new HashMap<>();
        var positionWithMultipleOptions = new Point(startingPosition.x, startingPosition.y);
        List<Point> multipleChoicePositions = new ArrayList<>();
        var currentPosition = new Point(startingPosition.x, startingPosition.y);
        var totalFreeSpace = totalFreeSpaces() + 1; // +1 for player;
        var stuck = false;

        System.out.println(totalFreeSpace + " free space");
        List<Point> moves = new ArrayList<>();

        while (true) {


            if (isAtEdge(currentPosition)) {
                return true;
            }

            if (playedPositions.size() == totalFreeSpace) {
                return false;
            }

            var availableMoves = availableMoves(currentPosition, playedPositions);
            if (availableMoves.size() > 1) {
                var p = new Point(currentPosition.x, currentPosition.y);
                multipleChoicePositions.add(p);
            }

            if (availableMoves.size() == 0) {
                // Finding any position where we had multiple choices
                var options = multipleChoicePositions.stream()
                        .map(i -> availableMoves(i, playedPositions))
                        .filter(i -> i.size() > 0).findAny();

                if (options.isEmpty()) {
                    return false;
                }
                currentPosition = options.get().get(0);
                playedPositions.put(new Point(currentPosition.x, currentPosition.y), true);
                continue;
            }

            var currentMove = availableMoves.get(0);
            currentPosition = new Point(currentMove.x, currentMove.y);
            playedPositions.put(currentPosition, true);
        }
    }

    private int totalFreeSpaces() {
        var freeSpaces = 0;
        for (var i = 0; i < mazeY; i++) {
            for (var j = 0; j < mazeX; j++) {
                var current = maze[i][j];
                if (current == ' ') {
                    freeSpaces++;
                }
            }
        }
        return freeSpaces;
    }

    private List<Point> availableMoves(Point p, Map<Point, Boolean> playedPoints) {

        var upPoint = new Point(p.x, p.y + 1);
        var downPoint = new Point(p.x, p.y - 1);
        var leftPoint = new Point(p.x - 1, p.y);
        var rightPoint = new Point(p.x + 1, p.y);

        return Stream.of(upPoint, downPoint, leftPoint, rightPoint)
                .filter(i -> canMove(i) && !playedPoints.containsKey(i)).toList();
    }

    private boolean canMove(Point point) {
        var isOutOfBounds = point.x < 0 || point.x >= mazeX || point.y < 0 || point.y >= mazeY;
        var currentPoint = maze[point.y][point.x];
        return !isOutOfBounds && currentPoint == ' ';
    }

    private boolean isAtEdge(Point p) {
        return p.x == 0 || p.x == mazeX - 1 || p.y == 0 || p.y == mazeY - 1;
    }

    private Point getPlayerPosition() {

        var playerCount = 0;
        Point player = null;
        for (var i = 0; i < mazeY; i++) {
            for (var j = 0; j < mazeX; j++) {
                var current = maze[i][j];
                if (current == 'k') {
                    playerCount++;
                    player = new Point(j, i);
                }
            }
        }
        if (playerCount != 1) {
            throw new IllegalArgumentException("Player less than or more than 1");
        }
        return player;
    }


}
