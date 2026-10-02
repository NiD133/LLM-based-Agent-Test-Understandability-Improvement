package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test10 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Comparing a character sequence with itself should yield a distance of zero,
     * since no edits are needed to transform the sequence into an identical copy.
     */
    @Test(timeout = 4000)
    public void distanceBetweenIdenticalSequencesIsZero() throws Throwable {
        final Integer threshold = 4;
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(threshold);

        // Both arguments are the same buffer, so the two sequences are identical.
        final CharBuffer sequence = CharBuffer.allocate(4);

        final Integer result = distance.apply((CharSequence) sequence, (CharSequence) sequence);

        assertEquals(0, (int) result);
    }
}
