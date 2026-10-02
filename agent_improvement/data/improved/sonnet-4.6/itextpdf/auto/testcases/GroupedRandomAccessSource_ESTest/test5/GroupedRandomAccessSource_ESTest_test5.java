package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test5 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that reading from a GroupedRandomAccessSource at a negative position
     * returns -1 (indicating an invalid/out-of-bounds read), and that the total
     * length of the grouped source equals the sum of its component sources' lengths.
     *
     * The group is composed of 8 sources backed by 6-byte arrays, giving a total
     * expected length of 8 * 6 = 48 bytes.
     */
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        // Create a 6-byte backing array shared across all sources in the group
        byte[] sixByteData = new byte[6];

        // Build three types of sources all backed by the same 6-byte array
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(sixByteData);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Compose a group of 8 sources (each contributing 6 bytes → total 48 bytes)
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

        // Attempt to read from a negative position — should return -1 (invalid read)
        int bytesRead = groupedSource.get(/* position */ (long) (-1), sixByteData, /* offset */ 1310, /* length */ (-156));

        assertEquals("Total length should be 8 sources × 6 bytes each", 48L, groupedSource.length());
        assertEquals("Reading from a negative position should return -1", (-1), bytesRead);
    }
}
