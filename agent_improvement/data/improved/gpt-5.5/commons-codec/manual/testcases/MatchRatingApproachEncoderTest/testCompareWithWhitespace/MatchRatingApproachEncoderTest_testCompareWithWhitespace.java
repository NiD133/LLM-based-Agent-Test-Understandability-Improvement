package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompareWithWhitespace {

    private static final String BRIAN = "Brian";
    private static final String BRYAN = "Bryan";

    private final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

    @Test
    final void testCompareWithWhitespace() {
        assertNamesEncodeEqual(BRIAN, BRYAN);

        assertNamesEncodeEqual(" Brian", BRYAN);
        assertNamesEncodeEqual("Brian ", BRYAN);
        assertNamesEncodeEqual(" Brian ", BRYAN);

        assertNamesEncodeEqual(BRIAN, " Bryan");
        assertNamesEncodeEqual(BRIAN, "Bryan ");
        assertNamesEncodeEqual(BRIAN, " Bryan ");
    }

    private void assertNamesEncodeEqual(final String firstName, final String secondName) {
        assertTrue(encoder.isEncodeEquals(firstName, secondName));
    }
}
