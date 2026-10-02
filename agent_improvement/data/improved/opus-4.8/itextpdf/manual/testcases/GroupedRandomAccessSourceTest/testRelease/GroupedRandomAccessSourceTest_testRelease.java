package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies that {@link GroupedRandomAccessSource} keeps at most one underlying
 * source "in use" at any time, releasing the previously active source before
 * activating the next one.
 *
 * <p>The group is built from three equally sized sources. In the group's
 * coordinate space they cover three contiguous, non-overlapping ranges:
 * <pre>
 *   source1 -> group offsets   0 ..  99
 *   source2 -> group offsets 100 .. 199
 *   source3 -> group offsets 200 .. 299
 * </pre>
 * Each {@code get(position)} that lands in a different source than the current
 * one must trigger exactly one release (of the old source) followed by one
 * acquire (of the new source), so the count of open sources never exceeds one.
 */
public class GroupedRandomAccessSourceTest_testRelease {

    /** Number of bytes backing each underlying source (and the size of each group range). */
    private static final int SOURCE_LENGTH = 100;

    /** Shared backing data containing the byte values 0, 1, 2, ... 99. */
    private byte[] data;

    @Before
    public void setUp() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < SOURCE_LENGTH; i++) {
            baos.write((byte) i);
        }
        data = baos.toByteArray();
    }

    @Test
    public void testRelease() throws Exception {
        RandomAccessSource source1 = new ArrayRandomAccessSource(data); // group offsets   0 ..  99
        RandomAccessSource source2 = new ArrayRandomAccessSource(data); // group offsets 100 .. 199
        RandomAccessSource source3 = new ArrayRandomAccessSource(data); // group offsets 200 .. 299
        RandomAccessSource[] sources = new RandomAccessSource[] { source1, source2, source3 };

        // Tracks the source the group currently reports as active, and how many
        // sources are open. Both are held in one-element arrays so the anonymous
        // GroupedRandomAccessSource subclass can mutate them.
        final RandomAccessSource[] activeSource = new RandomAccessSource[] { null };
        final int[] openSourceCount = new int[] { 0 };

        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(sources) {

            protected void sourceReleased(RandomAccessSource source) throws java.io.IOException {
                openSourceCount[0]--;
                if (activeSource[0] != source)
                    throw new AssertionFailedError("Released source isn't the current source");
                activeSource[0] = null;
            }

            protected void sourceInUse(RandomAccessSource source) throws java.io.IOException {
                if (activeSource[0] != null)
                    throw new AssertionFailedError("Current source wasn't released properly");
                openSourceCount[0]++;
                activeSource[0] = source;
            }
        };

        // Read two adjacent bytes from each range, hopping between sources. After
        // every hop exactly one source must be open: the new one, with the old released.
        // (The group constructor already activates the last source, so the count starts at 1.)
        readPair(grouped, 250); // source3
        assertExactlyOneSourceOpen(openSourceCount);

        readPair(grouped, 150); // hop to source2
        assertExactlyOneSourceOpen(openSourceCount);

        readPair(grouped, 50); // hop to source1
        assertExactlyOneSourceOpen(openSourceCount);

        readPair(grouped, 150); // hop back to source2
        assertExactlyOneSourceOpen(openSourceCount);

        readPair(grouped, 250); // hop back to source3
        assertExactlyOneSourceOpen(openSourceCount);

        grouped.close();
    }

    /** Reads two consecutive bytes ({@code position} and {@code position + 1}) from the group. */
    private void readPair(GroupedRandomAccessSource grouped, long position) throws Exception {
        grouped.get(position);
        grouped.get(position + 1);
    }

    private void assertExactlyOneSourceOpen(int[] openSourceCount) {
        Assert.assertEquals(1, openSourceCount[0]);
    }
}
