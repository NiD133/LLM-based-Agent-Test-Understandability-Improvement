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
     * A GroupedRandomAccessSource exposes several underlying sources as one
     * continuous source, so its total length is the sum of the lengths of all
     * the sources it groups together.
     *
     * Here every member source ultimately wraps the same 6-byte backing array,
     * and there are 8 members, so the grouped length is expected to be
     * 8 * 6 = 48 bytes. Closing the group must not change that reported length.
     */
    @Test(timeout = 4000)
    public void groupedLengthIsSumOfMemberSourceLengths() throws Throwable {
        final int BYTES_PER_SOURCE = 6;
        final int SOURCE_COUNT = 8;

        // A single 6-byte backing array shared by all the sources below.
        byte[] backingBytes = new byte[BYTES_PER_SOURCE];

        // Three different RandomAccessSource implementations, all 6 bytes long,
        // built on top of the same backing array.
        ArrayRandomAccessSource arraySource = new ArrayRandomAccessSource(backingBytes);
        GetBufferedRandomAccessSource bufferedSource = new GetBufferedRandomAccessSource(arraySource);
        IndependentRandomAccessSource independentSource = new IndependentRandomAccessSource(bufferedSource);

        // Group eight sources together (each contributes 6 bytes).
        RandomAccessSource[] memberSources = new RandomAccessSource[SOURCE_COUNT];
        memberSources[0] = bufferedSource;
        memberSources[1] = arraySource;
        memberSources[2] = bufferedSource;
        memberSources[3] = bufferedSource;
        memberSources[4] = independentSource;
        memberSources[5] = bufferedSource;
        memberSources[6] = arraySource;
        memberSources[7] = independentSource;

        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(memberSources);
        groupedSource.close();

        long expectedLength = (long) SOURCE_COUNT * BYTES_PER_SOURCE; // 48
        assertEquals(expectedLength, groupedSource.length());
    }
}
