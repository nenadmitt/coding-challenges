package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;

// https://www.codewars.com/kata/55dcdd2c5a73bdddcb000044
// Difficulty: 3kyu
public class RectanglesUnion {

    private class Rect {

        private static int idCounter = 0;
        private final int id;
        private final Rectangle rect;
        public Rect(int x, int y, int w, int h) {
            this.rect = new Rectangle(x,y,w,h);
            id = idCounter++;
        }
        public Optional<Rect> getIntersection(Rect o) {
            if (!rect.intersects(o.rect)) {
                return Optional.empty();
            }
            var i = rect.intersection(o.rect);
            return Optional.of(new Rect(i.x, i.y, i.width, i.height));
        }
        public int surface() {
            return rect.width * rect.height;
        }

        @Override
        public String toString() {
            return "Rect{" +
                    "rect=" + rect +
                    '}';
        }
    }

    public static int calculateSpace(int[][] rectangles) {
        return new RectanglesUnion()._calculateSpace(rectangles);
    }
    public int _calculateSpace(int[][] rectangles) {

        var rects = new LinkedList<Rect>();
        var unions = new LinkedList<Rect>();
        var result = 0;

        for (var i : rectangles) {

            var rectX = i[0];
            var rectY = i[1];
            var rectW = i[2] - rectX;
            var rectH = i[3] - rectY;

            var currentRect = new Rect(rectX, rectY, rectW, rectH);
            for (var prev: rects) {

                var u = currentRect.rect.union(prev.rect);
                var intersects = currentRect.getIntersection(prev);
                if (intersects.isPresent()) {
                    result = result - intersects.get().surface();
                    unions.add(intersects.get());
                    System.out.println(intersects.get());
                }
            }
            result += currentRect.surface();
            rects.add(currentRect);
        }

        System.out.println(rects);
        print(rects, unions);
        return result;
    }

    void print(List<Rect> a, List<Rect> b) {

        var c = new char[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                c[i][j] = 'o';
            }
        }

        a.forEach(r -> {
            var xF = r.rect.x;
            var xT = r.rect.x + r.rect.width;
            var yF = r.rect.y;
            var yT = r.rect.y + r.rect.height;
            for (int i = yF; i < yT; i++) {
                for (int j = xF; j < xT; j++) {
                    c[i][j] = '-';
                }
            }
        });
        b.forEach(r -> {
            var xF = r.rect.x;
            var xT = r.rect.x + r.rect.width;
            var yF = r.rect.y;
            var yT = r.rect.y + r.rect.height;
            for (int i = yF; i < yT; i++) {
                for (int j = xF; j < xT; j++) {
                    c[i][j] = '+';
                }
            }
        });

        for (var k : c) {
            System.out.println(Arrays.toString(k));
        }
    }
}
