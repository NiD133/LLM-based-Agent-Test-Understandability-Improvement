package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_Null_to_Nothing extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The MRA algorithm treats null as a trivial/empty input and returns an empty
     * string rather than throwing an exception.
     */
    @Test
    final void testGetEncoding_Null_to_Nothing() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        String result = encoder.encode((String) null);
        assertEquals("", result, "Encoding null should produce an empty string (NINO – Nothing In, Nothing Out)");
    }
}
