package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that names which sound alike collapse to the same NYSIIS code.
 *
 * <p>The two spellings "Trueman" and "Truman" are phonetically equivalent, so the
 * default (strict) NYSIIS encoder must reduce both to the canonical code "TRANAN".</p>
 */
public class NysiisTest_testTranan extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    @Test
    void testTranan() {
        final String expectedCode = "TRANAN";
        final String[] equivalentNames = { "Trueman", "Truman" };

        for (final String name : equivalentNames) {
            assertEquals(expectedCode, getStringEncoder().encode(name),
                    "Unexpected NYSIIS code for " + name);
        }
    }
}
