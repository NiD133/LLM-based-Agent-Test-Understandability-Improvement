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
 * Tests for {@link GroupedRandomAccessSource}, which stitches several underlying
 * {@link RandomAccessSource}s together and exposes them as one contiguous block of data.
 *
 * <p>Each test groups three identical sources, every one holding the same {@value #SOURCE_LENGTH}
 * bytes (the values 0..99). Because the sources are concatenated, the grouped source spans the
 * global offset range [0, {@value #GROUPED_LENGTH}):</p>
 * <ul>
 *   <li>source 1 -&gt; global offsets   0 .. 99</li>
 *   <li>source 2 -&gt; global offsets 100 .. 199</li>
 *   <li>source 3 -&gt; global offsets 200 .. 299</li>
 * </ul>
 */
public class GroupedRandomAccessSourceTest {

	/** Number of bytes held by each individual underlying source. */
	private static final int SOURCE_LENGTH = 100;

	/** Number of sources grouped together in every test. */
	private static final int SOURCE_COUNT = 3;

	/** Total length of the grouped source: every underlying source concatenated end to end. */
	private static final int GROUPED_LENGTH = SOURCE_LENGTH * SOURCE_COUNT;

	/** Shared backing array containing the byte values 0, 1, 2, ... 99. */
	private byte[] data;

	@Before
	public void setUp() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < SOURCE_LENGTH; i++) {
			baos.write((byte) i);
		}
		data = baos.toByteArray();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Builds a grouped source made of {@link #SOURCE_COUNT} sources, each backed by {@link #data}.
	 */
	private GroupedRandomAccessSource newGroupedSource() throws Exception {
		RandomAccessSource[] inputs = new RandomAccessSource[SOURCE_COUNT];
		for (int i = 0; i < SOURCE_COUNT; i++) {
			inputs[i] = new ArrayRandomAccessSource(data);
		}
		return new GroupedRandomAccessSource(inputs);
	}

	/**
	 * Single-byte reads should map a global offset to the right underlying source, and reads past
	 * the end of the grouped data should return -1.
	 */
	@Test
	public void testGet() throws Exception {
		ArrayRandomAccessSource source1 = new ArrayRandomAccessSource(data);
		ArrayRandomAccessSource source2 = new ArrayRandomAccessSource(data);
		ArrayRandomAccessSource source3 = new ArrayRandomAccessSource(data);

		RandomAccessSource[] inputs = new RandomAccessSource[]{
				source1, source2, source3
		};

		GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(inputs);

		// The grouped length is the sum of all the underlying source lengths.
		Assert.assertEquals(source1.length() + source2.length() + source3.length(), grouped.length());

		// Global offsets are translated into (source, local offset) pairs.
		Assert.assertEquals(source1.get(99), grouped.get(99));   // last byte of source 1
		Assert.assertEquals(source2.get(0),  grouped.get(100));  // first byte of source 2
		Assert.assertEquals(source2.get(1),  grouped.get(101));  // second byte of source 2
		Assert.assertEquals(source1.get(99), grouped.get(99));   // back into source 1 again
		Assert.assertEquals(source3.get(99), grouped.get(299));  // last byte of source 3

		// Reading past the end of the grouped data signals end-of-data with -1.
		Assert.assertEquals(-1, grouped.get(GROUPED_LENGTH));
	}

	/**
	 * Returns a byte array of {@code count} consecutive values starting at {@code start},
	 * matching the values produced by {@link #setUp()} (i.e. byte i holds the value i).
	 */
	private byte[] rangeArray(int start, int count) {
		byte[] result = new byte[count];
		for (int i = 0; i < count; i++) {
			result[i] = (byte) (i + start);
		}
		return result;
	}

	/**
	 * Asserts that {@code len} bytes of {@code expected} (starting at {@code expectedOffset})
	 * match {@code len} bytes of {@code actual} (starting at {@code actualOffset}).
	 */
	private void assertArrayEqual(byte[] expected, int expectedOffset, byte[] actual, int actualOffset, int len) {
		for (int i = 0; i < len; i++) {
			if (expected[i + expectedOffset] != actual[i + actualOffset]) {
				throw new AssertionFailedError("Differ at index " + (i + expectedOffset) + " and " + (i + actualOffset)
						+ " -> " + expected[i + expectedOffset] + " != " + actual[i + actualOffset]);
			}
		}
	}

	/**
	 * Bulk reads should copy bytes across source boundaries, never read more than the available
	 * data even when more is requested, and honour a non-zero starting offset.
	 */
	@Test
	public void testGetArray() throws Exception {
		GroupedRandomAccessSource grouped = newGroupedSource();

		byte[] out = new byte[500];

		// Reading the whole grouped range yields all GROUPED_LENGTH bytes: source 1, 2 then 3 back to back.
		Assert.assertEquals(GROUPED_LENGTH, grouped.get(0, out, 0, GROUPED_LENGTH));
		assertArrayEqual(rangeArray(0, 100), 0, out, 0, 100);
		assertArrayEqual(rangeArray(0, 100), 0, out, 100, 100);
		assertArrayEqual(rangeArray(0, 100), 0, out, 200, 100);

		// Requesting one byte more than exists still returns only the GROUPED_LENGTH available bytes.
		Assert.assertEquals(GROUPED_LENGTH, grouped.get(0, out, 0, GROUPED_LENGTH + 1));
		assertArrayEqual(rangeArray(0, 100), 0, out, 0, 100);
		assertArrayEqual(rangeArray(0, 100), 0, out, 100, 100);
		assertArrayEqual(rangeArray(0, 100), 0, out, 200, 100);

		// Reading 100 bytes from global offset 150 spans the source 2 / source 3 boundary:
		//   offsets 150..199 are the second half of source 2 (local 50..99),
		//   offsets 200..249 are the first half of source 3 (local 0..49).
		Assert.assertEquals(100, grouped.get(150, out, 0, 100));
		assertArrayEqual(rangeArray(50, 50), 0, out, 0, 50);
		assertArrayEqual(rangeArray(0, 50), 0, out, 50, 50);
	}

	/**
	 * As reads move between sources, the grouped source must release the previously active source
	 * before putting the next one in use, so that exactly one underlying source is ever open.
	 */
	@Test
	public void testRelease() throws Exception {
		RandomAccessSource[] sources = new RandomAccessSource[]{
				new ArrayRandomAccessSource(data),
				new ArrayRandomAccessSource(data),
				new ArrayRandomAccessSource(data)
		};

		// Tracks the currently active source and how many sources are open, updated via the
		// release/in-use callbacks below. Arrays are used so the anonymous subclass can mutate them.
		final RandomAccessSource[] currentSource = new RandomAccessSource[]{null};
		final int[] openCount = new int[]{0};

		GroupedRandomAccessSource grouped = new GroupedRandomAccessSource(sources) {
			protected void sourceReleased(RandomAccessSource source) throws java.io.IOException {
				openCount[0]--;
				if (currentSource[0] != source)
					throw new AssertionFailedError("Released source isn't the current source");
				currentSource[0] = null;
			}

			protected void sourceInUse(RandomAccessSource source) throws java.io.IOException {
				if (currentSource[0] != null)
					throw new AssertionFailedError("Current source wasn't released properly");
				openCount[0]++;
				currentSource[0] = source;
			}
		};

		// Each pair of reads stays within one source, then the next pair jumps to a different source.
		// After every jump the open count must remain 1, proving the old source was released first.
		grouped.get(250); // source 3
		grouped.get(251);
		Assert.assertEquals(1, openCount[0]);
		grouped.get(150); // source 2
		grouped.get(151);
		Assert.assertEquals(1, openCount[0]);
		grouped.get(50);  // source 1
		grouped.get(51);
		Assert.assertEquals(1, openCount[0]);
		grouped.get(150); // back to source 2
		grouped.get(151);
		Assert.assertEquals(1, openCount[0]);
		grouped.get(250); // back to source 3
		grouped.get(251);
		Assert.assertEquals(1, openCount[0]);

		grouped.close();
	}
}
