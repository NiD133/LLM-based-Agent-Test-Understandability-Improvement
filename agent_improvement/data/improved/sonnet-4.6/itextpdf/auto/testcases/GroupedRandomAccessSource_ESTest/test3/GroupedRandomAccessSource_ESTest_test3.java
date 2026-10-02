package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test3 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Accessing a GroupedRandomAccessSource at a negative position should return -1
     * (the out-of-bounds sentinel). The grouped source here spans 8 sub-sources of
     * 8 bytes each, so its total length must equal 64.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Base source: 8 zero bytes
        byte[] eightZeroBytes = new byte[8];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(eightZeroBytes);

        // Decorator sources wrapping the base source
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Group 8 sub-sources (mixed decorator types, each backed by 8 bytes)
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

        // Position -1 is before the start of the source; expect the sentinel value -1
        int resultAtNegativePosition = groupedSource.get(-1L);

        // Total length: 8 sub-sources x 8 bytes each = 64
        assertEquals(64L, groupedSource.length());
        assertEquals(-1, resultAtNegativePosition);
    }
}
