package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testConstructor2 {

    private static final class OffsetCase {
        private final byte[] buffer;
        private final int offset;
        private final int expectedAvailable;

        private OffsetCase(final byte[] buffer, final int offset, final int expectedAvailable) {
            this.buffer = buffer;
            this.offset = offset;
            this.expectedAvailable = expectedAvailable;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private void assertAvailableAfterConstruction(final OffsetCase offsetCase) {
        final UnsynchronizedByteArrayInputStream inputStream = newStream(offsetCase.buffer, offsetCase.offset);
        assertEquals(offsetCase.expectedAvailable, inputStream.available());
    }

    @Test
    // not necessary to close these resources
    @SuppressWarnings("resource")
    void testConstructor2() {
        final byte[] empty = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] one = new byte[1];
        final byte[] some = new byte[25];

        final OffsetCase[] offsetCases = {
                new OffsetCase(empty, 0, empty.length),
                new OffsetCase(empty, 1, 0),
                new OffsetCase(one, 0, one.length),
                new OffsetCase(one, 1, 0),
                new OffsetCase(one, 2, 0),
                new OffsetCase(some, 0, some.length),
                new OffsetCase(some, 1, some.length - 1),
                new OffsetCase(some, 10, some.length - 10),
                new OffsetCase(some, some.length, 0)
        };

        for (final OffsetCase offsetCase : offsetCases) {
            assertAvailableAfterConstruction(offsetCase);
        }
    }
}
