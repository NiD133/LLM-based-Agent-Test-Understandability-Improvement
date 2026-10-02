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
        // Four bytes in little-endian order representing the 32-bit value 0x80040302L.
        // byte[0]=0x02 (LSB), byte[1]=0x03, byte[2]=0x04, byte[3]=0x80 (MSB, unsigned 128).
        final byte[] littleEndianBytes = { 0x02, 0x03, 0x04, (byte) 0x80 };
        final long expectedValue = 0x80040302L;

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(littleEndianBytes);
        final long actualValue = fromLittleEndian(new InputStreamByteSupplier(inputStream), 4);

        assertEquals(expectedValue, actualValue);
    }
}
