package com.itextpdf.text.io;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies the bulk-read method
 * {@link GroupedRandomAccessSource#get(long, byte[], int, int)}.
 *
 * <p>The test builds a group out of three identical 100-byte sources. Because
 * the group exposes its sources as one contiguous block, the resulting view of
 * 300 bytes looks like the sequence {@code 0..99} repeated three times:
 *
 * <pre>
 *   group position : 0 .. 99   100 .. 199   200 .. 299
 *   byte value     : 0 .. 99     0 ..  99     0 ..  99
 *   backing source : source1     source2      source3
 * </pre>
 */
public class GroupedRandomAccessSourceTest_testGetArray {

    /** Number of bytes in each underlying source. */
    private static final int SOURCE_LENGTH = 100;

    /** Total number of bytes exposed by the group (three sources of 100 bytes). */
    private static final int GROUP_LENGTH = 3 * SOURCE_LENGTH;

    /** A single source's payload: the byte values 0, 1, 2, ... 99. */
    private byte[] sourceData;

    @Before
    public void setUp() {
        sourceData = sequentialBytes(0, SOURCE_LENGTH);
    }

    @Test
    public void testGetArray() throws Exception {
        // Group of three identical sources -> contiguous bytes [0..99][0..99][0..99].
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(new RandomAccessSource[] {
                new ArrayRandomAccessSource(sourceData),
                new ArrayRandomAccessSource(sourceData),
                new ArrayRandomAccessSource(sourceData)
        });

        // Generously sized destination so reads never run out of room.
        byte[] out = new byte[500];

        // Reading exactly the whole group returns all 300 bytes.
        Assert.assertEquals(GROUP_LENGTH, grouped.get(0, out, 0, GROUP_LENGTH));
        assertRegionEquals(sequentialBytes(0, 100), out, 0, 100);
        assertRegionEquals(sequentialBytes(0, 100), out, 100, 100);
        assertRegionEquals(sequentialBytes(0, 100), out, 200, 100);

        // Asking for one byte past the end is capped at the 300 bytes available.
        Assert.assertEquals(GROUP_LENGTH, grouped.get(0, out, 0, GROUP_LENGTH + 1));
        assertRegionEquals(sequentialBytes(0, 100), out, 0, 100);
        assertRegionEquals(sequentialBytes(0, 100), out, 100, 100);
        assertRegionEquals(sequentialBytes(0, 100), out, 200, 100);

        // Reading 100 bytes from position 150 spans the source2/source3 boundary:
        // positions 150..199 yield bytes 50..99, positions 200..249 yield bytes 0..49.
        Assert.assertEquals(100, grouped.get(150, out, 0, 100));
        assertRegionEquals(sequentialBytes(50, 50), out, 0, 50);
        assertRegionEquals(sequentialBytes(0, 50), out, 50, 50);
    }

    /**
     * Builds an array of {@code count} bytes holding the consecutive values
     * {@code start, start + 1, ... start + count - 1}.
     */
    private static byte[] sequentialBytes(int start, int count) {
        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[i] = (byte) (start + i);
        }
        return result;
    }

    /**
     * Asserts that {@code length} bytes of {@code expected} (starting at index 0)
     * match the slice of {@code actual} beginning at {@code actualOffset}.
     */
    private static void assertRegionEquals(byte[] expected, byte[] actual, int actualOffset, int length) {
        for (int i = 0; i < length; i++) {
            byte expectedByte = expected[i];
            byte actualByte = actual[i + actualOffset];
            if (expectedByte != actualByte) {
                throw new AssertionFailedError("Differ at index " + i + " and " + (i + actualOffset)
                        + " -> " + expectedByte + " != " + actualByte);
            }
        }
    }
}
