package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testDifference {

    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testDifference() throws EncoderException {
        final Object[][] edgeCases = {
            {0, null, null},
            {0, "", ""},
            {0, " ", " "}
        };

        final Object[][] commonNameComparisons = {
            {4, "Smith", "Smythe"},
            {2, "Ann", "Andrew"},
            {1, "Margaret", "Andrew"},
            {0, "Janet", "Margaret"}
        };

        final Object[][] microsoftDifferenceExamples = {
            {4, "Green", "Greene"},
            {0, "Blotchet-Halls", "Greene"}
        };

        final Object[][] microsoftSoundsLikeExamples = {
            {4, "Smith", "Smythe"},
            {4, "Smithers", "Smythers"},
            {2, "Anothers", "Brothers"}
        };

        assertDifferenceCases(edgeCases);
        assertDifferenceCases(commonNameComparisons);
        assertDifferenceCases(microsoftDifferenceExamples);
        assertDifferenceCases(microsoftSoundsLikeExamples);
    }

    private void assertDifferenceCases(final Object[][] cases) throws EncoderException {
        for (final Object[] testCase : cases) {
            assertDifference((Integer) testCase[0], (String) testCase[1], (String) testCase[2]);
        }
    }

    private void assertDifference(final int expectedDifference, final String left, final String right) throws EncoderException {
        assertEquals(expectedDifference, getStringEncoder().difference(left, right));
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }
}
