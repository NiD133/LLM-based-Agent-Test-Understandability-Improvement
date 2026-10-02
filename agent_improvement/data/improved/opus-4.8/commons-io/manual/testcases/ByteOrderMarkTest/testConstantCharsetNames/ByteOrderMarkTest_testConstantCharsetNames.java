package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;

/**
 * Tests that the charset name advertised by each predefined {@link ByteOrderMark}
 * constant is a real charset that can be resolved via {@link Charset#forName(String)}.
 */
public class ByteOrderMarkTest_testConstantCharsetNames {

    /**
     * Verifies that {@link ByteOrderMark#getCharsetName()} returns a name that
     * {@link Charset#forName(String)} can load for every predefined BOM constant.
     */
    @Test
    void testConstantCharsetNames() {
        assertCharsetNameResolves(ByteOrderMark.UTF_8);
        assertCharsetNameResolves(ByteOrderMark.UTF_16BE);
        assertCharsetNameResolves(ByteOrderMark.UTF_16LE);
        assertCharsetNameResolves(ByteOrderMark.UTF_32BE);
        assertCharsetNameResolves(ByteOrderMark.UTF_32LE);
    }

    /**
     * Asserts that the BOM's charset name can be resolved to a {@link Charset}.
     *
     * @param bom the BOM whose advertised charset name should be loadable.
     */
    private static void assertCharsetNameResolves(final ByteOrderMark bom) {
        assertNotNull(Charset.forName(bom.getCharsetName()));
    }
}
