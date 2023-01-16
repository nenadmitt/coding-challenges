package org.example;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class BracketsChallenge {

    private final Set<Character> openBrackets;
    private final Set<Character> closedBrackets;
    private final Map<Character,Character> bracketsMap;

    public BracketsChallenge() {

        openBrackets = Set.of('(','<','{','[');
        closedBrackets = Set.of(')','>','}',']');
        bracketsMap = Map.of(
                '(',')',
                '<','>',
                '{','}',
                '[',']'
        );
    }


    public boolean areBracketsClosed(String n) {

        if ( n.length() < 2 || n.length() > 10000) {
            throw new IllegalArgumentException();
        }

        Stack<Character> bracketStack = new Stack<>();

        for (var i =0; i < n.length(); i++) {

            var currentChar = n.charAt(i);
            if (isBracket(currentChar)) {

                if (isOpenBracket(currentChar)) {
                    bracketStack.push(currentChar);
                }
                else if (isClosedBracket(currentChar)) {

                    var previousBracket = bracketStack.peek();
                    var matches = isMatchingBracket(previousBracket, currentChar);

                    System.out.println(matches);

                    if (!matches) {
                        return false;
                    }else {
                        bracketStack.pop();
                    }
                }
            }
        }

        return bracketStack.isEmpty();
    }

    private boolean isBracket(char n) {
        return openBrackets.contains(n) || closedBrackets.contains(n);
    }

    private boolean isOpenBracket(char n) {
        return openBrackets.contains(n);
    }
    private boolean isClosedBracket(char n) {
        return closedBrackets.contains(n);
    }
    private boolean isMatchingBracket(char open, char close) {
        if (!bracketsMap.containsKey(open)) {
            return false;
        }
        return bracketsMap.get(open) == close;
    }
}
