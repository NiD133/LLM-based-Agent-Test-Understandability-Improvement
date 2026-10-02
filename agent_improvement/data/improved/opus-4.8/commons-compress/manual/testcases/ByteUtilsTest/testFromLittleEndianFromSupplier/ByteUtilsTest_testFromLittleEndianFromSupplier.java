package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromSupplier {

    @Test
    void testFromLittleEndianFromSupplier() throws IOException {
        // The supplier hands out bytes one at a time, in order: 2, 3, 4, then 5.
        final ByteArrayInputStream source = new ByteArrayInputStream(new byte[] { 2, 3, 4, 5 });
        final InputStreamByteSupplier supplier = new InputStreamByteSupplier(source);

        // Read the first 3 bytes (2, 3, 4) as a little-endian value, leaving the 5 unread.
        // In little-endian order each successive byte carries a weight 256x larger:
        //   byte 0 (value 2) -> weight 1
        //   byte 1 (value 3) -> weight 256
        //   byte 2 (value 4) -> weight 256 * 256
        final int bytesToRead = 3;
        final long expected = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expected, fromLittleEndian(supplier, bytesToRead));
    }
}
