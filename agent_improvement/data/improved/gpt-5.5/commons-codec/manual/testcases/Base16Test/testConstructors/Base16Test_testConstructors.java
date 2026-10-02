package org.apache.commons.codec.binary;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testConstructors {

    @Test
    @SuppressWarnings("deprecation")
    void testConstructors() {
        new Base16();
        new Base16(false);
        new Base16(true);
        new Base16(false, CodecPolicy.LENIENT);
        new Base16(false, CodecPolicy.STRICT);
    }
}
