package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests JaroWinklerSimilarity.apply(CharSequence, CharSequence).
 *
 * The left argument is always wrapped in a custom CharSequence whose equals()
 * intentionally delegates to String.equals(), ensuring the algorithm never
 * short-circuits by relying on reference or object equality of its inputs.
 */
public class JaroWinklerSimilarityTest_testGetJaroWinklerSimilarity_StringString {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    /**
     * Wraps the string in a custom {@link CharSequence}. This ensures that using
     * the {@link Object#equals(Object)} method on the input CharSequence to test
     * for equality will fail.
     */
    private static CharSequence wrap(final String string) {
        return new CharSequence() {

            @Override
            public char charAt(final int index) {
                return string.charAt(index);
            }

            @Override
            public boolean equals(final Object obj) {
                return string.equals(obj);
            }

            @Override
            public int hashCode() {
                return string.hashCode();
            }

            @Override
            public int length() {
                return string.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return string.subSequence(start, end);
            }

            @Override
            public String toString() {
                return string;
            }
        };
    }

    static Stream<Arguments> similarityTestCases() {
        return Stream.of(
            // --- Exact / identical inputs ---
            Arguments.of("both empty strings are perfectly similar",          "",                                  "",                            1.0d),
            Arguments.of("identical short strings are perfectly similar",     "foo",                               "foo",                         1.0d),

            // --- Trailing / leading whitespace variants ---
            Arguments.of("one trailing space slightly reduces similarity",    "foo",                               "foo ",                        0.94166d),
            Arguments.of("two trailing spaces reduce similarity more",        "foo",                               "foo  ",                       0.90666d),
            Arguments.of("surrounding spaces reduce similarity further",      "foo",                               " foo ",                       0.86666d),
            Arguments.of("two leading spaces reduce similarity significantly","foo",                               "  foo",                       0.51111d),

            // --- Substitution / deletion ---
            Arguments.of("one deletion between similar strings",              "frog",                              "fog",                         0.92499d),
            Arguments.of("completely dissimilar short strings score zero",    "fly",                               "ant",                         0.0d),
            Arguments.of("low similarity for very different words",           "elephant",                          "hippo",                       0.44166d),

            // --- Real-world / longer strings ---
            Arguments.of("abbreviated company name is highly similar",        "ABC Corporation",                   "ABC Corp",                    0.90666d),
            Arguments.of("punctuation variant of business name",              "D N H Enterprises Inc",             "D & H Enterprises, Inc.",     0.95251d),
            Arguments.of("apostrophe and period variants of fitness center",  "My Gym Children's Fitness Center",  "My Gym. Childrens Fitness",   0.942d),
            Arguments.of("misspelled US state name",                          "PENNSYLVANIA",                      "PENNCISYLVNIA",               0.898018d),
            Arguments.of("paths differing only in last character",            "/opt/software1",                    "/opt/software2",              0.971428d),
            Arguments.of("transposed characters in similar strings",          "aaabcd",                            "aaacdb",                      0.941666d),
            Arguments.of("names sharing a common prefix",                     "John Horn",                         "John Hopkins",                0.911111d)
        );
    }

    @DisplayName("Jaro-Winkler similarity with wrapped left CharSequence")
    @ParameterizedTest(name = "{0}")
    @MethodSource("similarityTestCases")
    void testGetJaroWinklerSimilarity_StringString(
            String description, String left, String right, double expectedSimilarity) {
        assertEquals(expectedSimilarity, similarity.apply(wrap(left), right), 0.00001d);
    }
}
