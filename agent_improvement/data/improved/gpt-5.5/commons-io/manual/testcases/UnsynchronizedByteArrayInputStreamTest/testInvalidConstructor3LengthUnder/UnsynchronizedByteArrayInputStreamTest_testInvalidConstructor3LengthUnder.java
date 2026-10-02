package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidConstructor3LengthUnder {

    @Test
    void testInvalidConstructor3LengthUnder() {
        assertThrows(IllegalArgumentException.class, () -> UnsynchronizedByteArrayInputStream.builder()
                .setByteArray(IOUtils.EMPTY_BYTE_ARRAY)
                .setOffset(0)
                .setLength(-1)
                .get());
    }
}
