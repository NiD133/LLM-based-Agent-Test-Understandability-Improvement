package com.itextpdf.text.io;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link GroupedRandomAccessSource#get(long)} and {@link GroupedRandomAccessSource#length()}.
 *
 * <p>A {@code GroupedRandomAccessSource} stitches several underlying sources together and exposes
 * them as one contiguous block of data. This test wraps three identical 100-byte sources, so the
 * grouped source behaves as a single 300-byte stream laid out as:
 * <pre>
 *   grouped index : [   0 .. 99 ] [ 100 .. 199 ] [ 200 .. 299 ]
 *   served by     :   source1       source2        source3
 * </pre>
 * Reading at or beyond index 300 is out of range and yields -1.
 */
public class GroupedRandomAccessSourceTest_testGet {

    /** Number of bytes in each underlying source. */
    private static final int SOURCE_SIZE = 100;

    /** Shared payload: the bytes 0, 1, 2, ... 99. Each underlying source is backed by this. */
    private byte[] data;

    @Before
    public void setUp() {
        data = new byte[SOURCE_SIZE];
        for (int i = 0; i < SOURCE_SIZE; i++) {
            data[i] = (byte) i;
        }
    }

    @Test
    public void testGet() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data);
        GroupedRandomAccessSource grouped =
                new GroupedRandomAccessSource(new RandomAccessSource[] { source1, source2, source3 });

        // The grouped length is the sum of the three underlying source lengths (300 bytes).
        assertEquals(source1.length() + source2.length() + source3.length(), grouped.length());

        // Last byte of the first source (grouped index 99 maps to source1 offset 99).
        assertEquals(source1.get(99), grouped.get(99));

        // First two bytes of the second source (grouped indices 100, 101 map to source2 offsets 0, 1).
        assertEquals(source2.get(0), grouped.get(100));
        assertEquals(source2.get(1), grouped.get(101));

        // Reading the first source again confirms re-reads still resolve correctly.
        assertEquals(source1.get(99), grouped.get(99));

        // Last byte of the third (final) source: grouped index 299 maps to source3 offset 99.
        assertEquals(source3.get(99), grouped.get(299));

        // Index 300 is one past the end of the grouped data, so get() reports end-of-data with -1.
        assertEquals(-1, grouped.get(300));
    }
}
