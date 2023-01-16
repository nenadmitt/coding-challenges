package org.example;

import java.util.Map;

public class ColoredTriangles {

    private final char RED = 'R';
    private final char BLUE = 'B';
    private final char GREEN = 'G';

    private final Map<String, Character> colorMap;

    public ColoredTriangles() {

        colorMap = Map.of(
                "GG", 'G',
                "BB", 'B',
                "RR", 'R',
                "BG", 'R',
                "GB", 'R',
                "RG", 'B',
                "GR", 'B',
                "BR", 'G',
                "RB", 'G');
    }

    public static char triangle(final String row) {
        return new ColoredTriangles().findLastColor(row);
    }

    private char findLastColor(String row) {

        if (row.length() == 1) {
            return row.charAt(0);
        }
        var rowCopy = row;;
        var result = new StringBuilder();
        while(rowCopy.length() > 1) {
            for (var i = 0; i < rowCopy.length() - 1; i ++) {
                var current = rowCopy.charAt(i);
                var next = rowCopy.charAt(i + 1);
                var color = getColor(current, next);
                result.append(color);
            }
            rowCopy = result.toString();
            result = new StringBuilder();
        }


        return rowCopy.charAt(0);
    }

    private char getColor(char a, char b) {

        var combination = a + String.valueOf(b);
        return colorMap.get(a + String.valueOf(b));
    }
}
