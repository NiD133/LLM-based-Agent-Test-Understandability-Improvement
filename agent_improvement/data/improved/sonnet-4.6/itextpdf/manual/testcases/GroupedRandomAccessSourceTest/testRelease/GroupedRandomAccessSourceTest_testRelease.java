package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies that GroupedRandomAccessSource properly releases a source when switching
 * to a different one, so only one underlying source is "in use" at any given time.
 */
public class GroupedRandomAccessSourceTest_testRelease {

    // 100-byte array with values 0..99, reused as backing data for each source
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
        // Three sources each covering 100 bytes; GroupedRandomAccessSource maps them
        // into a contiguous virtual address space:
        //   source1 → virtual bytes   0 –  99
        //   source2 → virtual bytes 100 – 199
        //   source3 → virtual bytes 200 – 299
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data);
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data);
        RandomAccessSource[] sources = new RandomAccessSource[] { source1, source2, source3 };

        // Single-element arrays are used so the anonymous subclass can mutate these
        // values while they remain effectively final from the enclosing scope.
        final RandomAccessSource[] currentActiveSource = new RandomAccessSource[] { null };
        final int[] activeSourceCount = new int[] { 0 };

        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(sources) {

            // Called when the grouped source finishes using a sub-source.
            // Ensures the released source is the one we expected to be active.
            protected void sourceReleased(RandomAccessSource source) throws java.io.IOException {
                activeSourceCount[0]--;
                if (currentActiveSource[0] != source)
                    throw new AssertionFailedError("Released source isn't the current source");
                currentActiveSource[0] = null;
            }

            // Called when the grouped source begins using a sub-source.
            // Ensures the previous source was fully released before a new one opens.
            protected void sourceInUse(RandomAccessSource source) throws java.io.IOException {
                if (currentActiveSource[0] != null)
                    throw new AssertionFailedError("Current source wasn't released properly");
                activeSourceCount[0]++;
                currentActiveSource[0] = source;
            }
        };

        // Access two bytes in source3 (virtual range 200–299)
        grouped.get(250);
        grouped.get(251);
        Assert.assertEquals("Only source3 should be active", 1, activeSourceCount[0]);

        // Switch to source2 (virtual range 100–199); source3 must have been released first
        grouped.get(150);
        grouped.get(151);
        Assert.assertEquals("Only source2 should be active", 1, activeSourceCount[0]);

        // Switch to source1 (virtual range 0–99); source2 must have been released first
        grouped.get(50);
        grouped.get(51);
        Assert.assertEquals("Only source1 should be active", 1, activeSourceCount[0]);

        // Switch back to source2; source1 must have been released first
        grouped.get(150);
        grouped.get(151);
        Assert.assertEquals("Only source2 should be active", 1, activeSourceCount[0]);

        // Switch back to source3; source2 must have been released first
        grouped.get(250);
        grouped.get(251);
        Assert.assertEquals("Only source3 should be active", 1, activeSourceCount[0]);

        grouped.close();
    }
}
