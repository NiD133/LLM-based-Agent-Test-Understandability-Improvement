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
        final byte[] availableBytes = { 2, 3 };
        final ByteArrayInputStream input = new ByteArrayInputStream(availableBytes);

        assertThrows(IOException.class, () -> fromLittleEndian(new InputStreamByteSupplier(input), 3));
    }
}
