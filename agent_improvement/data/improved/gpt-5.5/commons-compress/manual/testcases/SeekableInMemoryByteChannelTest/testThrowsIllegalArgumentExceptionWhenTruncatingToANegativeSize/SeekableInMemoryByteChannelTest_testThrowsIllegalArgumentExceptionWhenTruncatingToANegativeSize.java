package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsIllegalArgumentExceptionWhenTruncatingToANegativeSize {

    private static final long NEGATIVE_TRUNCATE_SIZE = -1;

    @Test
    void testThrowsIllegalArgumentExceptionWhenTruncatingToANegativeSize() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(NEGATIVE_TRUNCATE_SIZE));
        }
    }
}
