import org.example.BracketsChallenge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BracketChallengeTest {

    BracketsChallenge challenge = new BracketsChallenge();

    @Test
    void testValidBracketsProvided() {

        String input = "([<>]){<>}";
        Assertions.assertTrue(challenge.areBracketsClosed(input));
    }

    @Test
    void testBrackedNotProperlyClosed() {
        String input = "([1 + 1] >";
        Assertions.assertFalse(challenge.areBracketsClosed(input));
    }
}
