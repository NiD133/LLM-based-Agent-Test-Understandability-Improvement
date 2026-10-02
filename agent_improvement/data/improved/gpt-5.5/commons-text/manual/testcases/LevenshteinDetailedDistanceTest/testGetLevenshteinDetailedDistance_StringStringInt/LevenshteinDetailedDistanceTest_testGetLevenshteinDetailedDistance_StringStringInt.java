package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringStringInt {

    @ParameterizedTest
    @MethodSource("thresholdCases")
    void testGetLevenshteinDetailedDistance_StringStringInt(final Integer threshold, final String left, final String right,
            final int expectedDistance, final int expectedInsertCount, final int expectedDeleteCount, final int expectedSubstituteCount) {
        final LevenshteinResults result = new LevenshteinDetailedDistance(threshold).apply(left, right);

        assertLevenshteinResults(result, expectedDistance, expectedInsertCount, expectedDeleteCount, expectedSubstituteCount);
    }

    private static Stream<Arguments> thresholdCases() {
        return Stream.of(
                Arguments.of(0, "", "", 0, 0, 0, 0),
                Arguments.of(8, "aaapppp", "", 7, 0, 7, 0),
                Arguments.of(7, "aaapppp", "", 7, 0, 7, 0),
                Arguments.of(6, "aaapppp", "", -1, 0, 0, 0),
                Arguments.of(0, "b", "a", -1, 0, 0, 0),
                Arguments.of(0, "a", "b", -1, 0, 0, 0),
                Arguments.of(0, "aa", "aa", 0, 0, 0, 0),
                Arguments.of(2, "aa", "aa", 0, 0, 0, 0),
                Arguments.of(2, "aaa", "bbb", -1, 0, 0, 0),
                Arguments.of(3, "aaa", "bbb", 3, 0, 0, 3),
                Arguments.of(10, "aaaaaa", "b", 6, 0, 5, 1),
                Arguments.of(8, "aaapppp", "b", 7, 0, 6, 1),
                Arguments.of(4, "a", "bbb", 3, 2, 0, 1),
                Arguments.of(7, "aaapppp", "b", 7, 0, 6, 1),
                Arguments.of(3, "a", "bbb", 3, 2, 0, 1),
                Arguments.of(2, "a", "bbb", -1, 0, 0, 0),
                Arguments.of(2, "bbb", "a", -1, 0, 0, 0),
                Arguments.of(6, "aaapppp", "b", -1, 0, 0, 0),
                Arguments.of(1, "a", "bbb", -1, 0, 0, 0),
                Arguments.of(1, "bbb", "a", -1, 0, 0, 0),
                Arguments.of(1, "12345", "1234567", -1, 0, 0, 0),
                Arguments.of(1, "1234567", "12345", -1, 0, 0, 0),
                Arguments.of(1, "frog", "fog", 1, 0, 1, 0),
                Arguments.of(3, "fly", "ant", 3, 0, 0, 3),
                Arguments.of(7, "elephant", "hippo", 7, 0, 3, 4),
                Arguments.of(6, "elephant", "hippo", -1, 0, 0, 0),
                Arguments.of(7, "hippo", "elephant", 7, 3, 0, 4),
                Arguments.of(7, "hippo", "elephant", 7, 3, 0, 4),
                Arguments.of(6, "hippo", "elephant", -1, 0, 0, 0),
                Arguments.of(8, "hippo", "zzzzzzzz", 8, 3, 0, 5),
                Arguments.of(8, "zzzzzzzz", "hippo", 8, 0, 3, 5),
                Arguments.of(1, "hello", "hallo", 1, 0, 0, 1),
                Arguments.of(Integer.MAX_VALUE, "frog", "fog", 1, 0, 1, 0),
                Arguments.of(Integer.MAX_VALUE, "fly", "ant", 3, 0, 0, 3),
                Arguments.of(Integer.MAX_VALUE, "elephant", "hippo", 7, 0, 3, 4),
                Arguments.of(Integer.MAX_VALUE, "hippo", "elephant", 7, 3, 0, 4),
                Arguments.of(Integer.MAX_VALUE, "hippo", "zzzzzzzz", 8, 3, 0, 5),
                Arguments.of(Integer.MAX_VALUE, "zzzzzzzz", "hippo", 8, 0, 3, 5),
                Arguments.of(Integer.MAX_VALUE, "hello", "hallo", 1, 0, 0, 1));
    }

    private static void assertLevenshteinResults(final LevenshteinResults result, final int expectedDistance,
            final int expectedInsertCount, final int expectedDeleteCount, final int expectedSubstituteCount) {
        assertEquals(expectedDistance, result.getDistance());
        assertEquals(expectedInsertCount, result.getInsertCount());
        assertEquals(expectedDeleteCount, result.getDeleteCount());
        assertEquals(expectedSubstituteCount, result.getSubstituteCount());
    }
}
