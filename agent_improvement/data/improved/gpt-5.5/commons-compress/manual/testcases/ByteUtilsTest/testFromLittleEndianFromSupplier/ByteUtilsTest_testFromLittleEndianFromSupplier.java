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
        final byte[] littleEndianBytes = { 2, 3, 4, 5 };
        final ByteArrayInputStream input = new ByteArrayInputStream(littleEndianBytes);

        assertEquals(2 + 3 * 256 + 4 * 256 * 256, fromLittleEndian(new InputStreamByteSupplier(input), 3));
    }
}
