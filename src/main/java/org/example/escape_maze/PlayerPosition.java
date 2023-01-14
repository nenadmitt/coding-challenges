package org.example.escape_maze;

import org.example.EscapeMaze;

import java.util.Objects;

public class PlayerPosition {
    int x;
    int y;
    PlayerDirection direction;

    public PlayerPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PlayerPosition() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerPosition that)) return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "PlayerPosition{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                '}';
    }
}
