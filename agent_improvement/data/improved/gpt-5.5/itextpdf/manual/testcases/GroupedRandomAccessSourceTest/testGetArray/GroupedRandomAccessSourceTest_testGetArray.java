package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupedRandomAccessSourceTest_testGetArray {

    private static final int SINGLE_SOURCE_LENGTH = 100;
    private static final int GROUPED_SOURCE_COUNT = 3;
    private static final int GROUPED_LENGTH = SINGLE_SOURCE_LENGTH * GROUPED_SOURCE_COUNT;

    private byte[] data;

    @Before
    public void setUp() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < SINGLE_SOURCE_LENGTH; i++) {
            baos.write((byte) i);
        }
        data = baos.toByteArray();
    }

    @After
    public void tearDown() throws Exception {
    }

    private byte[] rangeArray(int start, int count) {
        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[i] = (byte) (i + start);
        }
        return result;
    }

    private void assertArrayEqual(byte[] a, int offa, byte[] b, int offb, int len) {
        for (int i = 0; i < len; i++) {
            int indexInA = i + offa;
            int indexInB = i + offb;
            if (a[indexInA] != b[indexInB]) {
                throw new AssertionFailedError("Differ at index " + indexInA + " and " + indexInB
                        + " -> " + a[indexInA] + " != " + b[indexInB]);
            }
        }
    }

    private void assertFullSourceCopiedAt(byte[] out, int outputOffset) {
        assertArrayEqual(rangeArray(0, SINGLE_SOURCE_LENGTH), 0, out, outputOffset, SINGLE_SOURCE_LENGTH);
    }

    @Test
    public void testGetArray() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data);
        RandomAccessSource[] inputs = new RandomAccessSource[] { source1, source2, source3 };
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(inputs);
        byte[] out = new byte[500];

        Assert.assertEquals(GROUPED_LENGTH, grouped.get(0, out, 0, GROUPED_LENGTH));
        assertFullSourceCopiedAt(out, 0);
        assertFullSourceCopiedAt(out, 100);
        assertFullSourceCopiedAt(out, 200);

        Assert.assertEquals(GROUPED_LENGTH, grouped.get(0, out, 0, 301));
        assertFullSourceCopiedAt(out, 0);
        assertFullSourceCopiedAt(out, 100);
        assertFullSourceCopiedAt(out, 200);

        Assert.assertEquals(100, grouped.get(150, out, 0, 100));
        assertArrayEqual(rangeArray(50, 50), 0, out, 0, 50);
        assertArrayEqual(rangeArray(0, 50), 0, out, 50, 50);
    }
}
