package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsIllegalArgumentExceptionWhenTruncatingToANegativeSize {

    /**
     * Verifies that truncating to a negative size throws IllegalArgumentException,
     * as required by the SeekableByteChannel contract: "If the new size is negative".
     */
    @Test
    void testThrowsIllegalArgumentExceptionWhenTruncatingToANegativeSize() throws Exception {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
        }
    }
}
