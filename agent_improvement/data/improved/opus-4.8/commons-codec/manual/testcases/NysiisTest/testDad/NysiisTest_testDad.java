package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testDad extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    @Test
    void testDad() {
        // "Dent" encodes to "DAD" (the "NT" ending is transcoded to "D").
        // Note: "Data Quality and Record Linkage Techniques" p.121 claims "DAN",
        // but the correct encoding is "DAD", as also verified with dropby.com.
        final String input = "Dent";
        final String expectedEncoding = "DAD";

        assertEquals(expectedEncoding, getStringEncoder().encode(input), "Problem with " + input);
    }
}
