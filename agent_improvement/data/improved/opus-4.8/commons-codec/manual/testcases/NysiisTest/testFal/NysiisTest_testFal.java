package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testFal extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    @Test
    void testFal() {
        final String input = "Phil";
        final String expectedEncoding = "FAL";

        final String actualEncoding = getStringEncoder().encode(input);

        assertEquals(expectedEncoding, actualEncoding, "Problem with " + input);
    }
}
