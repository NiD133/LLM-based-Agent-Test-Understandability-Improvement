package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_WithSpaces_SuccessfullyRemovedAndSpacesInvariant extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that {@code removeAccents} strips diacritical marks from each vowel while
     * leaving the surrounding space characters completely untouched.
     * <p>
     * Input:  "áé íó  ú"  — five accented vowels separated by spaces (including a double-space)
     * Output: "ae io  u"  — plain ASCII equivalents; space layout is identical
     */
    @Test
    final void testAccentRemoval_WithSpaces_SuccessfullyRemovedAndSpacesInvariant() {
        // Input string contains accented vowels: á→a, é→e, í→i, ó→o, ú→u.
        // The two single spaces and one double-space between them must be preserved as-is.
        String accentedInput   = "áé íó  ú";
        String expectedOutput  = "ae io  u";

        String actualOutput = getStringEncoder().removeAccents(accentedInput);

        assertEquals(expectedOutput, actualOutput,
                "removeAccents should replace each accented character with its plain ASCII "
                + "equivalent and leave all space characters unchanged");
    }
}
