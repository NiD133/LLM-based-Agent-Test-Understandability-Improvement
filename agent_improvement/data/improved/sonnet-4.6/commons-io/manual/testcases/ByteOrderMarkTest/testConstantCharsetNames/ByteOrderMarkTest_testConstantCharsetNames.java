package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testConstantCharsetNames {

    /**
     * Verifies that every predefined {@link ByteOrderMark} constant carries a charset name
     * that is recognised by the JVM. {@link Charset#forName(String)} returns a non-null
     * {@link Charset} on success and throws {@link java.nio.charset.UnsupportedCharsetException}
     * on failure, so a non-null result proves the name is valid.
     */
    @Test
    void testConstantCharsetNames() {
        assertNotNull(Charset.forName(ByteOrderMark.UTF_8.getCharsetName()),
                "UTF-8 charset name reported by ByteOrderMark.UTF_8 must be JVM-recognised");
        assertNotNull(Charset.forName(ByteOrderMark.UTF_16BE.getCharsetName()),
                "UTF-16BE charset name reported by ByteOrderMark.UTF_16BE must be JVM-recognised");
        assertNotNull(Charset.forName(ByteOrderMark.UTF_16LE.getCharsetName()),
                "UTF-16LE charset name reported by ByteOrderMark.UTF_16LE must be JVM-recognised");
        assertNotNull(Charset.forName(ByteOrderMark.UTF_32BE.getCharsetName()),
                "UTF-32BE charset name reported by ByteOrderMark.UTF_32BE must be JVM-recognised");
        assertNotNull(Charset.forName(ByteOrderMark.UTF_32LE.getCharsetName()),
                "UTF-32LE charset name reported by ByteOrderMark.UTF_32LE must be JVM-recognised");
    }
}
