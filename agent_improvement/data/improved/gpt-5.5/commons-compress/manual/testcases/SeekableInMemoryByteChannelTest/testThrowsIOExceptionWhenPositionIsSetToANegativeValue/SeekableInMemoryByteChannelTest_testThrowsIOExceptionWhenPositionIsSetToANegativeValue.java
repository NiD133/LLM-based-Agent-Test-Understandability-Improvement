package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsIOExceptionWhenPositionIsSetToANegativeValue {

    private static final long NEGATIVE_POSITION = -1L;

    @Test
    void testThrowsIOExceptionWhenPositionIsSetToANegativeValue() throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> channel.position(NEGATIVE_POSITION));
        }
    }
}
