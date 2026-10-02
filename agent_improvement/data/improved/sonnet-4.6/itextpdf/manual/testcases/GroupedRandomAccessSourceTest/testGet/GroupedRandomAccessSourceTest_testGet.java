package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that GroupedRandomAccessSource correctly delegates individual-byte reads
 * across a sequence of underlying ArrayRandomAccessSource instances.
 *
 * Layout of the grouped source (each sub-source holds 100 bytes, values 0..99):
 *   source1: global offsets   0 –  99
 *   source2: global offsets 100 – 199
 *   source3: global offsets 200 – 299
 *   offset 300 is one past the end → must return -1
 */
public class GroupedRandomAccessSourceTest_testGet {

    private static final int DATA_SIZE = 100;

    // Global offsets for boundary checks
    private static final int LAST_OFFSET_SOURCE1       = 99;   // last byte of source1
    private static final int FIRST_OFFSET_SOURCE2      = 100;  // first byte of source2
    private static final int SECOND_OFFSET_SOURCE2     = 101;  // second byte of source2
    private static final int LAST_OFFSET_SOURCE3       = 299;  // last byte of source3
    private static final int ONE_PAST_END_OF_GROUPED   = 300;  // out-of-bounds offset

    // Local (within-source) offsets that correspond to the global offsets above
    private static final int LOCAL_LAST  = 99;  // last byte index within a single source
    private static final int LOCAL_FIRST = 0;
    private static final int LOCAL_SECOND = 1;

    byte[] data;

    @Before
    public void setUp() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < DATA_SIZE; i++) {
            baos.write((byte) i);
        }
        data = baos.toByteArray();
    }

    @After
    public void tearDown() throws Exception {
    }

    private byte[] rangeArray(int start, int count) {
        byte[] rslt = new byte[count];
        for (int i = 0; i < count; i++) {
            rslt[i] = (byte) (i + start);
        }
        return rslt;
    }

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
    public void testGet() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data);

        RandomAccessSource[] sources = new RandomAccessSource[] { source1, source2, source3 };
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(sources);

        // Total length must equal the sum of all sub-source lengths
        Assert.assertEquals(source1.length() + source2.length() + source3.length(), grouped.length());

        // Reads within source1 (last byte)
        Assert.assertEquals(source1.get(LOCAL_LAST), grouped.get(LAST_OFFSET_SOURCE1));

        // Reads that cross from source1 into source2 (boundary region)
        Assert.assertEquals(source2.get(LOCAL_FIRST),  grouped.get(FIRST_OFFSET_SOURCE2));
        Assert.assertEquals(source2.get(LOCAL_SECOND), grouped.get(SECOND_OFFSET_SOURCE2));

        // Re-read within source1 to verify the source cursor can move backwards
        Assert.assertEquals(source1.get(LOCAL_LAST), grouped.get(LAST_OFFSET_SOURCE1));

        // Read the last byte of source3 (last valid byte of the grouped source)
        Assert.assertEquals(source3.get(LOCAL_LAST), grouped.get(LAST_OFFSET_SOURCE3));

        // Read one byte past the end of the grouped source — must return -1
        Assert.assertEquals(-1, grouped.get(ONE_PAST_END_OF_GROUPED));
    }
}
