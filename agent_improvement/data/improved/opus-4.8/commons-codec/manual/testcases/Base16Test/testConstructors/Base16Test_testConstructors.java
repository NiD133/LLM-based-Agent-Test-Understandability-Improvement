package org.apache.commons.codec.binary;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

/**
 * Verifies that every public {@link Base16} constructor can be invoked without
 * throwing, covering each available combination of constructor arguments.
 */
public class Base16Test_testConstructors {

    @Test
    void testConstructors() {
        // Default constructor (upper-case alphabet, lenient decoding).
        new Base16();

        // Boolean constructor selecting the alphabet case.
        new Base16(false); // upper-case alphabet
        new Base16(true);  // lower-case alphabet

        // Boolean + decoding-policy constructor.
        new Base16(false, CodecPolicy.LENIENT);
        new Base16(false, CodecPolicy.STRICT);
    }
}
