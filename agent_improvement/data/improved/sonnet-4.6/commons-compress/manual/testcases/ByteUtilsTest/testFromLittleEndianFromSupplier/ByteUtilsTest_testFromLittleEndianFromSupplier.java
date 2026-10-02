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
        // Input stream contains bytes [0x02, 0x03, 0x04, 0x05]; only first 3 are consumed.
        // Little-endian interpretation of [0x02, 0x03, 0x04]:
        //   0x02 * 256^0  +  0x03 * 256^1  +  0x04 * 256^2
        final byte[] inputBytes = { 2, 3, 4, 5 };
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBytes);
        final InputStreamByteSupplier supplier = new InputStreamByteSupplier(inputStream);

        final long expectedValue = 2 + 3 * 256L + 4 * 256L * 256L;
        final long actualValue = fromLittleEndian(supplier, 3);

        assertEquals(expectedValue, actualValue);
    }
}
