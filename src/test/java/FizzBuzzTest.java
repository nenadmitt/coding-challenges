import org.example.FizzBuzzChallenge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FizzBuzzTest {

    FizzBuzzChallenge challenge = new FizzBuzzChallenge();

    @Test
    void testArgumentOutOfBounds() {
        Assertions.assertThrows(IllegalArgumentException.class,() -> challenge.fizzBuzz(0));
        Assertions.assertThrows(IllegalArgumentException.class,() -> challenge.fizzBuzz(10001));
    }

    @Test
    void testReturnFizzBuzzIfDivisibleByThreeAndFive() {

        var resultSize = 30;
        var result = challenge.fizzBuzz(resultSize);

        Assertions.assertEquals(resultSize, result.size());

        var expectedFizzBuzzIndexes = List.of(14, 29);
        expectedFizzBuzzIndexes.forEach(i -> {
            Assertions.assertEquals("FizzBuzz", result.get(i));
        });
    }

    @Test
    void testReturnBuzzIfDivisibleByThree() {

        var resultSize = 15;
        var result = challenge.fizzBuzz(resultSize);

        Assertions.assertEquals(resultSize, result.size());

        var expectedBuzzIndexes = List.of(2, 5, 8, 11);
        expectedBuzzIndexes.forEach(i -> {
            Assertions.assertEquals("Buzz", result.get(i));
        });
    }

    @Test
    void testReturnFizzIfDivisibleByFive() {

        var resultSize = 23;
        var result = challenge.fizzBuzz(resultSize);

        Assertions.assertEquals(resultSize, result.size());

        var expectedFizzIndexes = List.of(4, 9, 19);
        expectedFizzIndexes.forEach(i -> {
            Assertions.assertEquals("Fizz", result.get(i));
        });
    }

    @Test
    void testPerformanceWithMaximumElements() {
        Assertions.assertDoesNotThrow(() -> challenge.fizzBuzz(10000));
    }
}
