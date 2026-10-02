package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LevenshteinDetailedDistanceTest_testGetLevenshteinDetailedDistance_StringString {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = LevenshteinDetailedDistance.getDefaultInstance();

    private static final ExpectedDistance[] EXPECTED_DISTANCES = {
        new ExpectedDistance("", "", 0, 0, 0, 0),
        new ExpectedDistance("", "a", 1, 1, 0, 0),
        new ExpectedDistance("aaapppp", "", 7, 0, 7, 0),
        new ExpectedDistance("frog", "fog", 1, 0, 1, 0),
        new ExpectedDistance("fly", "ant", 3, 0, 0, 3),
        new ExpectedDistance("elephant", "hippo", 7, 0, 3, 4),
        new ExpectedDistance("hippo", "elephant", 7, 3, 0, 4),
        new ExpectedDistance("hippo", "zzzzzzzz", 8, 3, 0, 5),
        new ExpectedDistance("zzzzzzzz", "hippo", 8, 0, 3, 5),
        new ExpectedDistance("hello", "hallo", 1, 0, 0, 1)
    };

    @ParameterizedTest
    @MethodSource("org.apache.commons.text.similarity.SimilarityInputTest#similarityInputs()")
    void testGetLevenshteinDetailedDistance_StringString(final Class<?> cls) {
        for (final ExpectedDistance expectedDistance : EXPECTED_DISTANCES) {
            assertDetailedDistance(cls, expectedDistance);
        }
    }

    private static void assertDetailedDistance(final Class<?> cls, final ExpectedDistance expectedDistance) {
        final LevenshteinResults result = UNLIMITED_DISTANCE.apply(
            SimilarityInputTest.build(cls, expectedDistance.left),
            SimilarityInputTest.build(cls, expectedDistance.right));

        assertEquals(expectedDistance.distance, result.getDistance());
        assertEquals(expectedDistance.insertCount, result.getInsertCount());
        assertEquals(expectedDistance.deleteCount, result.getDeleteCount());
        assertEquals(expectedDistance.substituteCount, result.getSubstituteCount());
    }

    private static final class ExpectedDistance {
        private final String left;
        private final String right;
        private final int distance;
        private final int insertCount;
        private final int deleteCount;
        private final int substituteCount;

        private ExpectedDistance(final String left, final String right, final int distance, final int insertCount, final int deleteCount,
                final int substituteCount) {
            this.left = left;
            this.right = right;
            this.distance = distance;
            this.insertCount = insertCount;
            this.deleteCount = deleteCount;
            this.substituteCount = substituteCount;
        }
    }
}

final class SimilarityInputTest {

    private SimilarityInputTest() {
    }

    static Stream<Class<?>> similarityInputs() {
        return Stream.of(String.class, StringBuilder.class, TextStringBuilder.class);
    }

    static SimilarityInput<Character> build(final Class<?> cls, final String value) {
        final CharSequence charSequence;
        if (cls == String.class) {
            charSequence = value;
        } else if (cls == StringBuilder.class) {
            charSequence = new StringBuilder(value);
        } else if (cls == TextStringBuilder.class) {
            charSequence = new TextStringBuilder(value);
        } else {
            throw new IllegalArgumentException("Unsupported input type: " + cls);
        }

        return new SimilarityInput<Character>() {
            @Override
            public Character at(final int index) {
                return charSequence.charAt(index);
            }

            @Override
            public int length() {
                return charSequence.length();
            }
        };
    }
}
