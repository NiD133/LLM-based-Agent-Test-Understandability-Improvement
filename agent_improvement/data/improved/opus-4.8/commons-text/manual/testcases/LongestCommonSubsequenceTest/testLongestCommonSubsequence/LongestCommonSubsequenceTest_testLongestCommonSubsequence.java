package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link LongestCommonSubsequence#longestCommonSubsequence(CharSequence, CharSequence)}.
 *
 * <p>The longest common subsequence (LCS) of two strings is the longest sequence of characters
 * that appears in both strings in the same relative order, but not necessarily contiguously.</p>
 */
public class LongestCommonSubsequenceTest_testLongestCommonSubsequence {

    private final LongestCommonSubsequence subject = new LongestCommonSubsequence();

    /**
     * Each case describes one (left, right) input pair and the expected longest common subsequence.
     * The leading text is only a human-readable label shown in the test report.
     */
    static Stream<Arguments> longestCommonSubsequenceCases() {
        return Stream.of(
            // No common subsequence -> empty result.
            Arguments.of("both inputs empty", "", "", ""),
            Arguments.of("right input empty", "left", "", ""),
            Arguments.of("left input empty", "", "right", ""),
            Arguments.of("single chars, nothing in common", "l", "a", ""),
            Arguments.of("no shared characters", "left", "a", ""),
            Arguments.of("completely different words", "fly", "ant", ""),

            // Partial overlap -> a shorter shared subsequence.
            Arguments.of("one character dropped", "frog", "fog", "fog"),
            Arguments.of("only a single shared letter", "elephant", "hippo", "h"),
            Arguments.of("shared trailing word", "ABC Corporation", "ABC Corp", "ABC Corp"),
            Arguments.of("punctuation differences",
                "D N H Enterprises Inc", "D & H Enterprises, Inc.", "D  H Enterprises Inc"),
            Arguments.of("apostrophe and spacing differences",
                "My Gym Children's Fitness Center", "My Gym. Childrens Fitness",
                "My Gym Childrens Fitness"),
            Arguments.of("transposed and inserted letters",
                "PENNSYLVANIA", "PENNCISYLVNIA", "PENNSYLVNIA"),
            Arguments.of("single shared letter in opposite words", "left", "right", "t"),
            Arguments.of("repeated shared letters", "leettteft", "ritttght", "tttt"),

            // Identical inputs -> the whole string is common.
            Arguments.of("identical inputs", "the same string", "the same string", "the same string")
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("longestCommonSubsequenceCases")
    void testLongestCommonSubsequence(final String description, final String left,
            final String right, final String expectedSubsequence) {
        assertEquals(expectedSubsequence, subject.longestCommonSubsequence(left, right));
    }
}
