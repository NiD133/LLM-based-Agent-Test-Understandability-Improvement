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
     * Verifies that a {@link GroupedRandomAccessSource} built from eight underlying
     * sources reports the combined length of all of them, and that a {@code get}
     * call made with a negative length reads nothing and returns -1.
     */
    @Test(timeout = 4000)
    public void get_withNegativeLength_readsNothingAndReturnsMinusOne() throws Throwable {
        // Every underlying source ultimately reads from this same 8-byte block,
        // so each source has a length of 8 bytes.
        final int SOURCE_LENGTH = 8;
        byte[] backingData = new byte[SOURCE_LENGTH];

        // Three flavours of source, all wrapping the same 8-byte block.
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingData);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Group eight sources together; the mix of source types is irrelevant here
        // because they all expose the same 8 bytes.
        RandomAccessSource[] groupedSources = {
                bufferedSource,
                arraySource,
                bufferedSource,
                bufferedSource,
                independentSource,
                bufferedSource,
                arraySource,
                independentSource
        };
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(groupedSources);

        // Read from position 0 but with a negative length: the read loop never runs,
        // so no bytes are copied and get() returns -1.
        int position = 0;
        int destinationOffset = -126;
        int negativeLength = -43;
        int bytesRead = groupedSource.get(position, backingData, destinationOffset, negativeLength);

        assertEquals(-1, bytesRead);

        // Total length is the sum of the eight underlying sources: 8 sources * 8 bytes.
        assertEquals(64L, groupedSource.length());
    }
}
