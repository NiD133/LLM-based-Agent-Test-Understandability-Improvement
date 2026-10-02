package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test4 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that a GroupedRandomAccessSource built from 8 sources (each backed
     * by a 7-byte array) reports the correct total length (56 bytes) and returns
     * 0 when reading the first byte (all bytes are zero-initialised).
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // Each source wraps a 7-byte zero-filled array, giving 56 bytes total across 8 sources.
        byte[] zeroBytes = new byte[7];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(zeroBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

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

        int firstByte = groupedSource.get(0L);

        assertEquals(56L, groupedSource.length());
        assertEquals(0, firstByte);
    }
}
