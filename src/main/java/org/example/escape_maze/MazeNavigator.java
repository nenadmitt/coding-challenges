package org.example.escape_maze;

import java.util.*;

public class MazeNavigator {

    private final EscapeMazeGame game;
    private PlayerPosition position;
    private final PlayerPosition startingPosition;
    private final int mazeX;
    private final int mazeY;
    private Map<PlayerPosition, Boolean> playedMoves;

    int ittrs = 0;
    public MazeNavigator(EscapeMazeGame game) {
        this.game = game;
        var maze = game.getMatrix();
        mazeX = maze.length;
        mazeY = maze[0].length;
        position = findStartingCoordinates();
        startingPosition = new PlayerPosition(position.x, position.y);
        startingPosition.direction = position.direction;
        playedMoves = new HashMap<>();
    }

    public List<Character> findExit() {

        var exitFound = false;
        var random = new Random();
        var result = new StringBuilder();
        var moveList = new LinkedList<PlayerPosition>();

        while(!exitFound) {
            var moveOptions = getAvailableMoveOptions();

            if (moveOptions.size() == 0) {
                position = new PlayerPosition(startingPosition.x, startingPosition.y);
                playedMoves.clear();
                moveList.clear();
            }else {

                var randomMove = 0;
                randomMove = random.nextInt(moveOptions.size());
                var currentMove = moveOptions.get(randomMove);

                play(currentMove);
                moveList.add(currentMove);

                if (escaped()) {
                    exitFound = true;
                }
            }
        }
        System.out.println(startingPosition.direction);
        return toMoveListString(moveList);
    }

    private List<Character> toMoveListString(List<PlayerPosition> moves) {

        var forward = 'F';
        var left = 'L';
        var right = 'R';
        var back = 'B';

        List<Character> moveList = new LinkedList<>();

        var currentDirection = startingPosition.direction;


        for ( var i = 0; i < moves.size(); i++ ) {

            var current = moves.get(i);
            if (currentDirection == PlayerDirection.UP) {
                if (current.direction == PlayerDirection.DOWN) {
                    moveList.add(back);
                }else if (current.direction == PlayerDirection.RIGHT) {
                    moveList.add(right);
                }else if (current.direction == PlayerDirection.LEFT) {
                    moveList.add(left);
                }
            }

            if (currentDirection == PlayerDirection.DOWN) {
                if (current.direction == PlayerDirection.UP) {
                    moveList.add(back);
                }else if (current.direction == PlayerDirection.RIGHT) {
                    moveList.add(left);
                }else if (current.direction == PlayerDirection.LEFT) {
                    moveList.add(right);
                }
            }

            if (currentDirection == PlayerDirection.RIGHT) {
                if (current.direction == PlayerDirection.UP) {
                    moveList.add(left);
                }else if (current.direction == PlayerDirection.DOWN) {
                    moveList.add(right);
                }else if (current.direction == PlayerDirection.LEFT) {
                    moveList.add(back);
                }
            }

            if (currentDirection == PlayerDirection.LEFT) {
                if (current.direction == PlayerDirection.UP) {
                    moveList.add(right);
                }else if (current.direction == PlayerDirection.DOWN) {
                    moveList.add(left);
                }else if (current.direction == PlayerDirection.RIGHT) {
                    moveList.add(back);
                }
            }
            moveList.add(forward);
            currentDirection = current.direction;
        }

        return moveList;
    }

    private void play(PlayerPosition next) {
        this.playedMoves.put(new PlayerPosition(position.x, position.y), true);
        this.position.x = next.x;
        this.position.y = next.y;
    }

    private List<PlayerPosition> getAvailableMoveOptions() {
        var current = new PlayerPosition(position.x, position.y);

        List<PlayerPosition> availableMoves = new LinkedList<>();

        var leftMove = new PlayerPosition(current.x, current.y - 1);
        leftMove.direction = PlayerDirection.LEFT;
        if (isMoveAvailable(leftMove.x, leftMove.y) && !playedMoves.containsKey(leftMove)) {
            availableMoves.add(leftMove);
        }

        var rightMove = new PlayerPosition(current.x, current.y + 1);
        rightMove.direction = PlayerDirection.RIGHT;
        if (isMoveAvailable(rightMove.x, rightMove.y) && !playedMoves.containsKey(rightMove)) {
            availableMoves.add(rightMove);
        }

        var upMove = new PlayerPosition(current.x - 1, current.y);
        upMove.direction = PlayerDirection.UP;
        if (isMoveAvailable(upMove.x, upMove.y) && !playedMoves.containsKey(upMove)) {
            availableMoves.add(upMove);
        }

        var downMove = new PlayerPosition(current.x + 1, current.y);
        downMove.direction = PlayerDirection.DOWN;
        if (isMoveAvailable(downMove.x, downMove.y) && !playedMoves.containsKey(downMove)) {
            availableMoves.add(downMove);
        }
        return availableMoves;
    }

    private boolean isMoveAvailable(int x, int y) {
        return game.getMatrix()[x][y] == ' ';
    }

    private PlayerPosition findStartingCoordinates() {

        var maze = game.getMatrix();
        var cords = Set.of('<','>','^','v');
        var p = new PlayerPosition();
        for (var i = 0; i < mazeX - 1; i++) {
            for (var j = 0; j < mazeY - 1; j++) {
                var current = maze[i][j];
                System.out.println(String.format("x:%s, y:%s", i, j));
                if (cords.contains(current)) {
                    p.x = i;
                    p.y = j;

                    switch (current) {
                        case '<' -> p.direction = PlayerDirection.LEFT;
                        case '>' -> p.direction = PlayerDirection.RIGHT;
                        case '^' -> p.direction = PlayerDirection.UP;
                        case 'v' -> p.direction = PlayerDirection.DOWN;
                    }
                    return p;
                }
            }
        }
        throw new IllegalArgumentException("Starting position not found.");
    }

    public boolean escaped() {
        return position.x == 0 || position.x == mazeX - 1 || position.y == 0 || position.y == mazeY - 1;
    }
}
