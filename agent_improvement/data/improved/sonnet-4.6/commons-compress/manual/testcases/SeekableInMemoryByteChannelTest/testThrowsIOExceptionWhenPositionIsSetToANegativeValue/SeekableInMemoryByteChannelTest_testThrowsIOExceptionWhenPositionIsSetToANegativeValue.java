package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowsIOExceptionWhenPositionIsSetToANegativeValue {

    /*
     * Setting a negative position violates the channel contract. The implementation
     * throws IllegalArgumentException (rather than the IOException mentioned in the
     * SeekableByteChannel Javadoc) to signal that the caller passed an invalid argument.
     */
    @Test
    void testThrowsIOExceptionWhenPositionIsSetToANegativeValue() throws Exception {
        try (SeekableByteChannel c = new SeekableInMemoryByteChannel()) {
            assertThrows(IllegalArgumentException.class, () -> c.position(-1));
        }
    }
}
