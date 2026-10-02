package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test6 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that a partial read near the end of a GroupedRandomAccessSource
     * returns only the bytes that are actually available.
     *
     * The source wraps a single 6-byte backing array, so its total length is 6.
     * Reading 4 bytes starting at offset 4 can only yield the 2 remaining bytes
     * (positions 4 and 5), so get(...) is expected to return 2.
     */
    @Test(timeout = 4000)
    public void readingPastEndReturnsOnlyAvailableBytes() throws Throwable {
        final int SOURCE_SIZE = 6;
        byte[] backingData = new byte[SOURCE_SIZE];

        ArrayRandomAccessSource backingSource = new ArrayRandomAccessSource(backingData);
        RandomAccessSource[] groupedSources = new RandomAccessSource[] { backingSource };
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(groupedSources);

        long readPosition = 4L;
        int destinationOffset = 4;
        int bytesRequested = 4;
        int bytesRead = groupedSource.get(readPosition, backingData, destinationOffset, bytesRequested);

        assertEquals((long) SOURCE_SIZE, groupedSource.length());
        assertEquals(2, bytesRead);
    }
}
