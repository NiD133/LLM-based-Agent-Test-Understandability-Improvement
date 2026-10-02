package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromSupplierUnsignedInt32 {

    @Test
    void testFromLittleEndianFromSupplierUnsignedInt32() throws IOException {
        final byte[] littleEndianUnsignedInt32 = { 2, 3, 4, (byte) 128 };
        final int unsignedInt32ByteCount = 4;
        final long expectedUnsignedInt32Value = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

        final ByteArrayInputStream input = new ByteArrayInputStream(littleEndianUnsignedInt32);

        assertEquals(expectedUnsignedInt32Value, fromLittleEndian(new InputStreamByteSupplier(input), unsignedInt32ByteCount));
    }
}
