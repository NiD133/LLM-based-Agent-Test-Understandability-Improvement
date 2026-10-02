package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test08 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * The Levenshtein distance between a sequence and itself is always zero,
     * regardless of the configured threshold. Here the same two-character
     * buffer is compared against itself using the largest possible threshold.
     */
    @Test(timeout = 4000)
    public void distanceBetweenIdenticalSequencesIsZero() throws Throwable {
        CharSequence identicalSequence = CharBuffer.wrap(new char[2]);
        LevenshteinDetailedDistance distanceWithMaxThreshold =
                new LevenshteinDetailedDistance(Integer.MAX_VALUE);

        LevenshteinResults results =
                distanceWithMaxThreshold.apply(identicalSequence, identicalSequence);

        assertEquals(0, (int) results.getDistance());
    }
}
