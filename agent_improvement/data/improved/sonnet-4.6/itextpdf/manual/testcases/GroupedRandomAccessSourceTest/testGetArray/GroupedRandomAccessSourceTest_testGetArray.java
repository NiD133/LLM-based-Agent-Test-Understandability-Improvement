package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests GroupedRandomAccessSource.get(), which reads bytes across multiple
 * underlying sources as if they were one contiguous stream.
 *
 * The fixture builds a grouped source from three 100-byte arrays (bytes 0–99
 * each), giving a virtual address space of 300 bytes total:
 *   virtual [0..99]   → source1 bytes 0–99
 *   virtual [100..199] → source2 bytes 0–99
 *   virtual [200..299] → source3 bytes 0–99
 */
public class GroupedRandomAccessSourceTest_testGetArray {

    /** Raw data for each source: bytes 0, 1, 2, …, 99. */
    byte[] data;

    @Before
    public void setUp() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 100; i++) {
            baos.write((byte) i);
        }
        data = baos.toByteArray();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Builds a byte array of {@code count} consecutive values starting at {@code start}.
     * Used to express expected content as "bytes 50–99" rather than magic literals.
     */
    private byte[] rangeArray(int start, int count) {
        byte[] rslt = new byte[count];
        for (int i = 0; i < count; i++) {
            rslt[i] = (byte) (i + start);
        }
        return rslt;
    }

    /**
     * Asserts that {@code len} bytes taken from {@code a} at {@code offa}
     * match the bytes taken from {@code b} at {@code offb}.
     */
    private void assertArrayEqual(byte[] a, int offa, byte[] b, int offb, int len) {
        for (int i = 0; i < len; i++) {
            if (a[i + offa] != b[i + offb]) {
                throw new AssertionFailedError(
                        "Differ at index " + (i + offa) + " and " + (i + offb)
                        + " -> " + a[i + offa] + " != " + b[i + offb]);
            }
        }
    }

    @Test
    public void testGetArray() throws Exception {
        // Build three independent 100-byte sources.
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data); // virtual 0–99
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data); // virtual 100–199
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data); // virtual 200–299
        RandomAccessSource[] sources = new RandomAccessSource[] { source1, source2, source3 };
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(sources);

        byte[] out = new byte[500];

        // --- Scenario 1: read exactly 300 bytes starting at virtual position 0 ---
        // Covers all three sources completely; should return 300 bytes read.
        Assert.assertEquals(300, grouped.get(0, out, 0, 300));
        // out[0..99]   must equal source1 data (bytes 0–99)
        assertArrayEqual(rangeArray(0, 100), 0, out, 0, 100);
        // out[100..199] must equal source2 data (bytes 0–99)
        assertArrayEqual(rangeArray(0, 100), 0, out, 100, 100);
        // out[200..299] must equal source3 data (bytes 0–99)
        assertArrayEqual(rangeArray(0, 100), 0, out, 200, 100);

        // --- Scenario 2: request 301 bytes when only 300 are available ---
        // GroupedRandomAccessSource must clamp the result to 300 (actual available bytes).
        Assert.assertEquals(300, grouped.get(0, out, 0, 301));
        // Content in out[0..299] must be the same as in Scenario 1.
        assertArrayEqual(rangeArray(0, 100), 0, out, 0, 100);
        assertArrayEqual(rangeArray(0, 100), 0, out, 100, 100);
        assertArrayEqual(rangeArray(0, 100), 0, out, 200, 100);

        // --- Scenario 3: read 100 bytes starting at virtual position 150 ---
        // Position 150 is 50 bytes into source2 (source2 starts at virtual 100).
        // The first 50 bytes (virtual 150–199) come from source2 at local offset 50 → bytes 50–99.
        // The next 50 bytes (virtual 200–249) come from source3 at local offset 0 → bytes 0–49.
        Assert.assertEquals(100, grouped.get(150, out, 0, 100));
        // out[0..49]  must equal bytes 50–99 (tail of source2)
        assertArrayEqual(rangeArray(50, 50), 0, out, 0, 50);
        // out[50..99] must equal bytes 0–49  (head of source3)
        assertArrayEqual(rangeArray(0, 50), 0, out, 50, 50);
    }
}
