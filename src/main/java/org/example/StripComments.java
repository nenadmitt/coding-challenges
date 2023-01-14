package org.example;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StripComments {

    public static String strip(String text, String[] commentSymbols) {

        var result = new StringBuilder();

        var commentSet = Arrays.stream(commentSymbols).map(c -> c.charAt(0)).collect(Collectors.toSet());
        var stripStarted = false;

        for (var i = 0; i < text.length(); i ++) {
            var c = text.charAt(i);
            var isCommentChar = commentSet.contains(c);
            if (isCommentChar) {
                stripStarted = true;
            }

            if (!stripStarted) {
                result.append(c);
            }

            if (c == '\n' && stripStarted) {
                stripStarted = false;

                if (result.length() > 0) {
                    var lastChar = result.charAt(result.length() - 1);
                    if (lastChar == ' '){
                        result.deleteCharAt(result.length() - 1);
                    }
                }

                result.append('\n');
            }
        }

        var lastIndex = result.length() - 1;
        if (result.charAt(lastIndex) == '\n' || result.charAt(lastIndex) == ' ') {
            result.deleteCharAt(lastIndex);
        }

        return result.toString();
    }
}
