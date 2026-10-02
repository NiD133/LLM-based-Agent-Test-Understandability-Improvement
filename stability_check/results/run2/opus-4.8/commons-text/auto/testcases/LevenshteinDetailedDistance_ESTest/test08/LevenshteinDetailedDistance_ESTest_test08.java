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
     * Comparing a character sequence against itself must yield a Levenshtein
     * distance of zero, even when a (very large) threshold is configured.
     */
    @Test(timeout = 4000)
    public void distanceOfSequenceAgainstItselfIsZero() throws Throwable {
        CharBuffer sameSequence = CharBuffer.wrap(new char[2]);
        LevenshteinDetailedDistance distanceWithMaxThreshold =
                new LevenshteinDetailedDistance(Integer.valueOf(Integer.MAX_VALUE));

        LevenshteinResults results =
                distanceWithMaxThreshold.apply((CharSequence) sameSequence, (CharSequence) sameSequence);

        assertEquals(0, (int) results.getDistance());
    }
}
