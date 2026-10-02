package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test2 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that GroupedRandomAccessSource returns -1 when get() is called with
     * an invalid (negative) offset, and that the total length correctly reflects
     * the sum of all grouped sources.
     *
     * The grouped source contains 8 sub-sources, each backed by an 8-byte array,
     * so the expected total length is 64 bytes.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Each source is backed by the same 8-byte zero array
        byte[] eightZeroBytes = new byte[8];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(eightZeroBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Build a group of 8 sources using the three source instances above
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

        // Calling get() with a negative offset (-126) is invalid and should return -1
        int bytesRead = groupedSource.get(
            /* position */ (long) (byte) 0,
            /* dest     */ eightZeroBytes,
            /* offset   */ (int) (byte) (-126),
            /* length   */ (int) (byte) (-43)
        );

        assertEquals(-1, bytesRead);

        // 8 sources × 8 bytes each = 64 bytes total length
        assertEquals(64L, groupedSource.length());
    }
}
