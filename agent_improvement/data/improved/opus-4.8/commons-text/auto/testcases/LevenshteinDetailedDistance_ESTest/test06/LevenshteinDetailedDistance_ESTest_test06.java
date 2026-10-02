package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test06 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When a sequence is compared against itself, no edits are required, so the
     * Levenshtein distance must be zero. Here a threshold of 0 is used, which
     * still allows the (zero) distance to be reported.
     */
    @Test(timeout = 4000)
    public void distanceOfSequenceWithItselfIsZero() throws Throwable {
        LevenshteinDetailedDistance distanceWithZeroThreshold =
                new LevenshteinDetailedDistance(Integer.valueOf(0));

        // An empty 24-character CharBuffer acts as the sequence compared with itself.
        CharBuffer sequence = CharBuffer.allocate(24);

        LevenshteinResults results =
                distanceWithZeroThreshold.apply((CharSequence) sequence, (CharSequence) sequence);

        assertEquals(0, (int) results.getDistance());
    }
}
