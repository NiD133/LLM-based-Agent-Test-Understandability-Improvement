package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidConstructor2OffsetUnder {

    @Test
    void testInvalidConstructor2OffsetUnder() {
        assertThrows(IllegalArgumentException.class, () -> UnsynchronizedByteArrayInputStream.builder()
                .setByteArray(IOUtils.EMPTY_BYTE_ARRAY)
                .setOffset(-1)
                .get());
    }
}
