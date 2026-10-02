package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test6 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that reading near the end of the source returns only the bytes
     * that are actually available, even when more bytes are requested.
     *
     * The source has 6 bytes total. Reading 4 bytes starting at position 4
     * can only return 2 bytes (positions 4 and 5), so the return value should be 2.
     */
    @Test(timeout = 4000)
    public void test6() throws Throwable {
        // Create a 6-byte source (all zeros)
        byte[] sourceData = new byte[6];
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(sourceData);

        // Wrap it in a GroupedRandomAccessSource with a single source segment
        RandomAccessSource[] sourceParts = new RandomAccessSource[1];
        sourceParts[0] = (RandomAccessSource) arraySource;
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sourceParts);

        // Read 4 bytes starting at position 4 into sourceData at offset 4;
        // only 2 bytes (positions 4–5) are available, so 2 should be returned
        int bytesRead = groupedSource.get((long) 4, sourceData, 4, 4);

        assertEquals(6L, groupedSource.length());
        assertEquals(2, bytesRead);
    }
}
