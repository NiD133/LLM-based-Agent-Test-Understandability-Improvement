/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.text.io;

import java.io.ByteArrayOutputStream;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link GroupedRandomAccessSource}, which concatenates multiple
 * {@link RandomAccessSource} instances into a single contiguous address space.
 *
 * <p>Each test groups three {@link ArrayRandomAccessSource} instances, each
 * backed by the same 100-byte data array, giving a 300-byte grouped source
 * with the layout:
 * <pre>
 *   source1: global positions   0 –  99  (local values 0x00 – 0x63)
 *   source2: global positions 100 – 199  (local values 0x00 – 0x63)
 *   source3: global positions 200 – 299  (local values 0x00 – 0x63)
 * </pre>
 */
public class GroupedRandomAccessSourceTest {

    /** Number of bytes in each underlying source array. */
    private static final int DATA_SIZE = 100;

    /** Shared byte array used to back all test sources (values 0x00 – 0x63). */
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

    /**
     * Verifies single-byte reads via {@link GroupedRandomAccessSource#get(long)}.
     *
     * <p>Checks that the grouped source correctly maps global positions to the
     * appropriate underlying source, handles backward seeks (re-reading an
     * earlier source), and returns {@code -1} for positions beyond the end.
     */
    @Test
    public void testGet() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data); // positions   0 –  99
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data); // positions 100 – 199
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data); // positions 200 – 299

        RandomAccessSource[] inputs = new RandomAccessSource[]{source1, source2, source3};
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(inputs);

        // Total length must equal the sum of all individual source lengths.
        Assert.assertEquals(source1.length() + source2.length() + source3.length(), grouped.length());

        // Read the last byte of source1 and the first two bytes of source2.
        Assert.assertEquals(source1.get(99), grouped.get(99));
        Assert.assertEquals(source2.get(0),  grouped.get(100));
        Assert.assertEquals(source2.get(1),  grouped.get(101));

        // Backward seek into source1 must still return the correct byte.
        Assert.assertEquals(source1.get(99), grouped.get(99));

        // Read the last byte of source3 (last valid position in the grouped source).
        Assert.assertEquals(source3.get(99), grouped.get(299));

        // A position one past the end must return -1.
        Assert.assertEquals(-1, grouped.get(300));
    }

    /**
     * Verifies bulk reads via {@link GroupedRandomAccessSource#get(long, byte[], int, int)}.
     *
     * <p>Covers three scenarios:
     * <ol>
     *   <li>Reading exactly all available bytes (fits perfectly).</li>
     *   <li>Requesting more bytes than available (capped at actual length).</li>
     *   <li>Starting mid-source and crossing a source boundary.</li>
     * </ol>
     */
    @Test
    public void testGetArray() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data); // positions   0 –  99
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data); // positions 100 – 199
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data); // positions 200 – 299

        RandomAccessSource[] inputs = new RandomAccessSource[]{source1, source2, source3};
        GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(inputs);

        byte[] out = new byte[500];

        // Scenario 1: read exactly all 300 available bytes from position 0.
        Assert.assertEquals(300, grouped.get(0, out, 0, 300));
        assertArrayEqual(rangeArray(0, 100), 0, out, 0,   100); // source1 bytes
        assertArrayEqual(rangeArray(0, 100), 0, out, 100, 100); // source2 bytes
        assertArrayEqual(rangeArray(0, 100), 0, out, 200, 100); // source3 bytes

        // Scenario 2: request 301 bytes — only 300 exist, so 300 are returned.
        Assert.assertEquals(300, grouped.get(0, out, 0, 301));
        assertArrayEqual(rangeArray(0, 100), 0, out, 0,   100); // source1 bytes
        assertArrayEqual(rangeArray(0, 100), 0, out, 100, 100); // source2 bytes
        assertArrayEqual(rangeArray(0, 100), 0, out, 200, 100); // source3 bytes

        // Scenario 3: read 100 bytes starting at position 150 (mid-source2 → into source3).
        Assert.assertEquals(100, grouped.get(150, out, 0, 100));
        assertArrayEqual(rangeArray(50, 50), 0, out, 0,  50); // last 50 bytes of source2 (values 50–99)
        assertArrayEqual(rangeArray(0,  50), 0, out, 50, 50); // first 50 bytes of source3 (values 0–49)
    }

    /**
     * Verifies that {@link GroupedRandomAccessSource} invokes
     * {@code sourceInUse} when switching to a new underlying source and
     * {@code sourceReleased} when done with the previous one, keeping exactly
     * one source active at any point in time.
     */
    @Test
    public void testRelease() throws Exception {
        ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data); // positions   0 –  99
        ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data); // positions 100 – 199
        ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data); // positions 200 – 299

        RandomAccessSource[] sources = new RandomAccessSource[]{source1, source2, source3};

        // Track which source is currently open and how many are open concurrently.
        final RandomAccessSource[] currentSource = new RandomAccessSource[]{null};
        final int[] openSourceCount = new int[]{0};

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

        // Access source3, then backward-seek to source2, then to source1.
        // Each switch must release the old source before opening the new one.
        grouped.get(250);
        grouped.get(251);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.get(150);
        grouped.get(151);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.get(50);
        grouped.get(51);
        Assert.assertEquals(1, openSourceCount[0]);

        // Forward-seek back to source2, then source3 — still only 1 open at any time.
        grouped.get(150);
        grouped.get(151);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.get(250);
        grouped.get(251);
        Assert.assertEquals(1, openSourceCount[0]);

        grouped.close();
    }

    /** Returns a byte array of {@code count} bytes with values {@code start, start+1, ..., start+count-1}. */
    private byte[] rangeArray(int start, int count) {
        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[i] = (byte) (i + start);
        }
        return result;
    }

    /**
     * Asserts that {@code len} bytes from {@code a[offa..]} equal those from {@code b[offb..]},
     * throwing {@link AssertionFailedError} with a descriptive message on the first mismatch.
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
}
