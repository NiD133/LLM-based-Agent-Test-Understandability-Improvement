package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromSupplierThrowsForPrematureEnd {

    @Test
    void testFromLittleEndianFromSupplierThrowsForPrematureEnd() {
        // The stream holds only 2 bytes, but we ask for 3 bytes.
        // fromLittleEndian must throw IOException when the supplier is exhausted early.
        final ByteArrayInputStream streamWithTwoBytes = new ByteArrayInputStream(new byte[] { 2, 3 });
        final InputStreamByteSupplier supplier = new InputStreamByteSupplier(streamWithTwoBytes);
        final int bytesRequested = 3;

        assertThrows(IOException.class, () -> fromLittleEndian(supplier, bytesRequested));
    }
}
