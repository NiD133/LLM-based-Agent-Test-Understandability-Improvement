package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test1 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that reading from a GroupedRandomAccessSource returns -1 when the
     * underlying WindowRandomAccessSource starts beyond the end of the backing array,
     * and that the reported length reflects the window size rather than the backing array size.
     *
     * Setup:
     *  - A 6-byte backing array is wrapped in an ArrayRandomAccessSource.
     *  - A WindowRandomAccessSource is created with offset 807 and length 807,
     *    which places the window entirely past the end of the 6-byte array.
     *  - That window is the sole source in a GroupedRandomAccessSource.
     *
     * Expected behaviour:
     *  - get() returns -1 because the read position (196) combined with the window
     *    offset (807) is out of bounds for the backing array.
     *  - length() returns 807, the declared window length.
     */
    @Test(timeout = 4000)
    public void test_getReturnsMinusOneWhenWindowExceedsBacking_andLengthMatchesWindowSize() throws Throwable {
        // Backing array of 6 bytes (all zeros)
        byte[] backingArray = new byte[6];
        ArrayRandomAccessSource backingSource = new ArrayRandomAccessSource(backingArray);

        // Window starting at byte 807 with length 807 — entirely outside the 6-byte backing array
        WindowRandomAccessSource windowSource = new WindowRandomAccessSource(backingSource, 807L, 807L);

        // Group the single window source
        RandomAccessSource[] sources = new RandomAccessSource[1];
        sources[0] = (RandomAccessSource) windowSource;
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        // Attempt to read at position 196 into backingArray with offset 1318 and length 1318;
        // the read is out of bounds for the underlying array, so -1 is expected.
        int bytesRead = groupedSource.get(196L, backingArray, 1318, 1318);
        assertEquals((-1), bytesRead);

        // The grouped source length equals the declared window length, not the backing array size
        assertEquals(807L, groupedSource.length());
    }
}
