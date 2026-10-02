package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test0 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that GroupedRandomAccessSource.length() returns the total combined length
     * of all its constituent sources even after the grouped source has been closed.
     *
     * Setup: 8 sources backed by a 6-byte array, giving a total length of 48 bytes.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // A single 6-byte array shared by all sources in the group
        byte[] sixBytes = new byte[6];

        // Three distinct source types all reading from the same underlying byte array
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(sixBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Build a group of 8 sources — each wraps the same 6 bytes, so total length = 8 * 6 = 48
        RandomAccessSource[] sources = new RandomAccessSource[8];
        sources[0] = bufferedSource;
        sources[1] = arraySource;
        sources[2] = bufferedSource;
        sources[3] = bufferedSource;
        sources[4] = independentSource;
        sources[5] = bufferedSource;
        sources[6] = arraySource;
        sources[7] = independentSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        // Close the grouped source; length() should still reflect the total byte count
        groupedSource.close();

        assertEquals(48L, groupedSource.length());
    }
}
