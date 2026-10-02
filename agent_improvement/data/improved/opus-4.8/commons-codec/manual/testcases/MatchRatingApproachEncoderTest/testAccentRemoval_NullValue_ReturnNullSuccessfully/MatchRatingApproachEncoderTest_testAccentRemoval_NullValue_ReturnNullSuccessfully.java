package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#removeAccents(String)} with a {@code null} input.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_NullValue_ReturnNullSuccessfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * {@code removeAccents} should treat a {@code null} word as a no-op and return {@code null}
     * rather than throwing a {@link NullPointerException}.
     */
    @Test
    final void removeAccentsReturnsNullForNullInput() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final String result = encoder.removeAccents(null);

        assertNull(result, "removeAccents(null) should return null");
    }
}
