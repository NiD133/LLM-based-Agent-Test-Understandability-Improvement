package org.apache.commons.compress.harmony.unpack200;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test00 extends SegmentUtils_ESTest_scaffolding {

    /**
     * When every inner flags array is empty, {@link SegmentUtils#countMatches(long[][], IMatcher)}
     * never inspects a flag, so it returns 0 without ever calling the matcher. This is why a
     * {@code null} matcher is safe here and does not trigger a NullPointerException.
     */
    @Test(timeout = 4000)
    public void countMatchesReturnsZeroForAllEmptyRows() throws Throwable {
        long[] emptyRow = new long[0];
        long[][] flagsWithFiveEmptyRows = new long[5][6];
        flagsWithFiveEmptyRows[0] = emptyRow;
        flagsWithFiveEmptyRows[1] = emptyRow;
        flagsWithFiveEmptyRows[2] = emptyRow;
        flagsWithFiveEmptyRows[3] = emptyRow;
        flagsWithFiveEmptyRows[4] = emptyRow;

        int matchCount = SegmentUtils.countMatches(flagsWithFiveEmptyRows, (IMatcher) null);

        assertEquals(0, matchCount);
    }
}
