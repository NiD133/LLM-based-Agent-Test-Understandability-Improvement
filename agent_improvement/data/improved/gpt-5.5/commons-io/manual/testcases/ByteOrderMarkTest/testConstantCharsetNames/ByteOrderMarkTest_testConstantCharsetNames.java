package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testConstantCharsetNames {

    private static final List<ByteOrderMark> STANDARD_BOMS = Arrays.asList(
            ByteOrderMark.UTF_8,
            ByteOrderMark.UTF_16BE,
            ByteOrderMark.UTF_16LE,
            ByteOrderMark.UTF_32BE,
            ByteOrderMark.UTF_32LE);

    /**
     * Tests that {@link ByteOrderMark#getCharsetName()} can be loaded as a
     * {@link java.nio.charset.Charset} as advertised.
     */
    @Test
    void testConstantCharsetNames() {
        for (final ByteOrderMark byteOrderMark : STANDARD_BOMS) {
            assertNotNull(Charset.forName(byteOrderMark.getCharsetName()));
        }
    }
}
