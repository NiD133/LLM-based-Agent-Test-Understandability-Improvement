package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(ByteUtils.ByteSupplier, int)} fails fast when the
 * supplier runs out of bytes before the requested number has been read.
 */
public class ByteUtilsTest_testFromLittleEndianFromSupplierThrowsForPrematureEnd {

    @Test
    void testFromLittleEndianFromSupplierThrowsForPrematureEnd() {
        // The supplier can provide only 2 bytes...
        final ByteArrayInputStream source = new ByteArrayInputStream(new byte[] { 2, 3 });
        final InputStreamByteSupplier supplier = new InputStreamByteSupplier(source);

        // ...but we ask fromLittleEndian to read 3, so it must throw on the premature end of data.
        final int bytesToRead = 3;
        assertThrows(IOException.class, () -> fromLittleEndian(supplier, bytesToRead));
    }
}
