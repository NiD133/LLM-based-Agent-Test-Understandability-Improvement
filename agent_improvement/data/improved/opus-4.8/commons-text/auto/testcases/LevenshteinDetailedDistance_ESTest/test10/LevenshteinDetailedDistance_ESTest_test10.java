package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test10 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the "right" sequence is empty, transforming a non-empty "left" sequence
     * into it requires deleting every character of "left". With a threshold large
     * enough to cover that distance, the limited algorithm reports the full delete
     * count rather than giving up (returning -1).
     */
    @Test(timeout = 4000)
    public void transformingIntoEmptySequenceCountsOnlyDeletions() throws Throwable {
        final int length = 1470;

        // Threshold equal to the sequence length, so the distance stays within bounds.
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(length);

        // "left": a sequence of 1470 characters (a freshly allocated buffer's contents).
        CharBuffer left = CharBuffer.allocate(length);

        // "right": an empty view (start == end == 1470) over the same buffer.
        CharBuffer right = CharBuffer.wrap((CharSequence) left, length, length);

        LevenshteinResults results = distance.apply((CharSequence) left, (CharSequence) right);

        // Every character of "left" must be deleted to reach the empty "right".
        assertEquals(length, (int) results.getDistance());
        assertEquals(0, (int) results.getInsertCount());
        assertEquals(length, (int) results.getDeleteCount());
        assertEquals(0, (int) results.getSubstituteCount());
    }
}
