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
     * A GroupedRandomAccessSource concatenates several sources end to end.
     * Here we group eight sources, each backed by an 8-byte buffer, so the
     * combined length is 8 * 8 = 64 bytes. Reading at a negative offset is
     * out of range, so get(...) must report end-of-data by returning -1.
     */
    @Test(timeout = 4000)
    public void readingAtNegativeOffsetReturnsEndOfData() throws Throwable {
        // Build three views over a single 8-byte buffer; each reports length 8.
        byte[] eightByteBuffer = new byte[8];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(eightByteBuffer);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Group eight length-8 sources, giving a total length of 64 bytes.
        RandomAccessSource[] sources = {
            bufferedSource,
            arraySource,
            bufferedSource,
            bufferedSource,
            independentSource,
            bufferedSource,
            arraySource,
            independentSource,
        };
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        int byteAtNegativeOffset = groupedSource.get(-1L);

        assertEquals(64L, groupedSource.length());
        assertEquals(-1, byteAtNegativeOffset);
    }
}
