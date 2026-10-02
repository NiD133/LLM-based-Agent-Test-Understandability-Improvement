package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupedRandomAccessSourceTest_testRelease {

    private static final int DATA_LENGTH = 100;
    private static final int FIRST_SOURCE_POSITION = 50;
    private static final int SECOND_SOURCE_POSITION = 150;
    private static final int THIRD_SOURCE_POSITION = 250;

    private byte[] data;

    @Before
    public void setUp() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < DATA_LENGTH; i++) {
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
                throw new AssertionFailedError("Differ at index " + (i + offa) + " and " + (i + offb) + " -> " + a[i + offa] + " != " + b[i + offb]);
            }
        }
    }

    @Test
    public void testRelease() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data);
        RandomAccessSource[] sources = new RandomAccessSource[] { source1, source2, source3 };

        final RandomAccessSource[] currentSource = new RandomAccessSource[] { null };
        final int[] openSourceCount = new int[] { 0 };
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(sources) {

            @Override
            protected void sourceReleased(RandomAccessSource source) throws java.io.IOException {
                openSourceCount[0]--;
                if (currentSource[0] != source) {
                    throw new AssertionFailedError("Released source isn't the current source");
                }
                currentSource[0] = null;
            }

            @Override
            protected void sourceInUse(RandomAccessSource source) throws java.io.IOException {
                if (currentSource[0] != null) {
                    throw new AssertionFailedError("Current source wasn't released properly");
                }
                openSourceCount[0]++;
                currentSource[0] = source;
            }
        };

        grouped.get(THIRD_SOURCE_POSITION);
        grouped.get(THIRD_SOURCE_POSITION + 1);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.get(SECOND_SOURCE_POSITION);
        grouped.get(SECOND_SOURCE_POSITION + 1);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.get(FIRST_SOURCE_POSITION);
        grouped.get(FIRST_SOURCE_POSITION + 1);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.get(SECOND_SOURCE_POSITION);
        grouped.get(SECOND_SOURCE_POSITION + 1);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.get(THIRD_SOURCE_POSITION);
        grouped.get(THIRD_SOURCE_POSITION + 1);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.close();
    }
}
