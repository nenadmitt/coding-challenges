package org.example;

import java.awt.Point;
import java.util.*;


// https://www.codewars.com/kata/5abeaf0fee5c575ff20000e4
// Dificulty: 3kyu
public class Finder {
    class Path {
        List<String> moves = new LinkedList<>();
        int cost = 0;

        public Path(List<String> moves, int cost) {
            this.moves = moves;
            this.cost = cost;
        }

        public Path() {
        }

        public Path copy() {
            return new Path(new LinkedList<>(moves),cost);
        }

        @Override
        public String toString() {
            return "Path{" +
                    "moves=" + moves +
                    ", cost=" + cost +
                    '}';
        }
    }
    int[][] field;
    int FIELD_W;
    int FIELD_H;
    Set<Point> visited = new HashSet<>();
    Path result = new Path(new ArrayList<>(), Integer.MAX_VALUE);

    public static List<String> cheapestPath(int[][] field, Point a, Point b) {
        return new Finder()._cheapestPath(field, new Point(a.y, a.x), new Point(b.y, b.x));
    }

    public List<String> _cheapestPath(int[][] field, Point start, Point finish) {
        this.field = field;
        FIELD_H = field.length;
        FIELD_W = field[0].length;

        dfs(start, finish, new Path(), "current");

        return result.moves.subList(1, result.moves.size());
    }

    public void dfs(Point current, Point end, Path path, String play) {

        if (outOfBounds(current) || visited.contains(current)) {
            return;
        }

        var cost = field[current.y][current.x];
        visited.add(current);
        path.moves.add(play);
        path.cost += cost;

        if (current.equals(end)) {
            if (result.cost > path.cost) {
                result = path.copy();
            }
        } else {
            dfs(new Point(current.x + 1, current.y), end, path, "right");
            dfs(new Point(current.x - 1, current.y), end, path, "left");
            dfs(new Point(current.x, current.y + 1), end, path, "down");
            dfs(new Point(current.x, current.y - 1), end, path, "up");
        }

        path.moves.remove(path.moves.size() - 1);
        path.cost -= cost;
        visited.remove(current);
    }

    private boolean outOfBounds(Point p) {
        return p.x < 0 || p.x >= FIELD_W || p.y < 0 || p.y >= FIELD_H;
    }
}
