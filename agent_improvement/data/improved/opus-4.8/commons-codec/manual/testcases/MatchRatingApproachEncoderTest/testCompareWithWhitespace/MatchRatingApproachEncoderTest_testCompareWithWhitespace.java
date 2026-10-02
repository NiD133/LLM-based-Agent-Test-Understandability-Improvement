package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats
 * "Brian" and "Bryan" as homophones (phonetically equal) regardless of any leading or
 * trailing whitespace on either name.
 *
 * <p>The encoder strips whitespace while cleaning the names, so surrounding spaces must not
 * change the comparison result.</p>
 */
public class MatchRatingApproachEncoderTest_testCompareWithWhitespace
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    /** The two names under test are phonetically equal even without any whitespace. */
    private static final String NAME = "Brian";
    private static final String HOMOPHONE = "Bryan";

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /** Asserts that the encoder considers the two given names phonetically equal. */
    private void assertEncodesEqual(final String name1, final String name2) {
        assertTrue(getStringEncoder().isEncodeEquals(name1, name2),
                () -> "Expected \"" + name1 + "\" and \"" + name2 + "\" to encode as equal");
    }

    @Test
    final void testCompareWithWhitespace() {
        // Baseline: the two names are equal with no surrounding whitespace.
        assertEncodesEqual(NAME, HOMOPHONE);

        // Whitespace around the first name must not affect the result.
        assertEncodesEqual(" Brian", HOMOPHONE);   // leading space
        assertEncodesEqual("Brian ", HOMOPHONE);   // trailing space
        assertEncodesEqual(" Brian ", HOMOPHONE);  // both sides

        // Whitespace around the second name must not affect the result either.
        assertEncodesEqual(NAME, " Bryan");        // leading space
        assertEncodesEqual(NAME, "Bryan ");        // trailing space
        assertEncodesEqual(NAME, " Bryan ");       // both sides
    }
}
