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

    /** Each underlying source wraps this same 8-byte backing buffer. */
    private static final int SOURCE_LENGTH = 8;

    /** The group is built from 8 sources, so its total length is 8 * 8. */
    private static final int GROUP_COUNT = 8;

    @Test(timeout = 4000)
    public void getWithNegativeOffsetAndLengthReturnsMinusOne() throws Throwable {
        // Build a chain of sources that all read from the same 8-byte buffer.
        byte[] buffer = new byte[SOURCE_LENGTH];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(buffer);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Group of 8 sources, each SOURCE_LENGTH bytes long.
        RandomAccessSource[] sources = new RandomAccessSource[GROUP_COUNT];
        sources[0] = bufferedSource;
        sources[1] = arraySource;
        sources[2] = bufferedSource;
        sources[3] = bufferedSource;
        sources[4] = independentSource;
        sources[5] = bufferedSource;
        sources[6] = arraySource;
        sources[7] = independentSource;
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        // Reading with a negative target offset and length signals "nothing read" (-1).
        int bytesRead = groupedSource.get(0L, buffer, (byte) -126, (byte) -43);

        assertEquals(-1, bytesRead);
        assertEquals((long) (GROUP_COUNT * SOURCE_LENGTH), groupedSource.length());
    }
}
