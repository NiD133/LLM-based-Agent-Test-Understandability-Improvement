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
public class LevenshteinDetailedDistance_ESTest_test12 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing two empty CharBuffers with a distance threshold of 0
     * produces a result with zero insertions, deletions, substitutions, and total distance.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Integer threshold = new Integer(0);
        LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(threshold);

        CharBuffer emptyBuffer = CharBuffer.allocate(0);
        LevenshteinResults results = distanceWithZeroThreshold.apply((CharSequence) emptyBuffer, (CharSequence) emptyBuffer);

        assertEquals(0, (int) results.getInsertCount());
        assertEquals(0, (int) results.getDeleteCount());
        assertEquals(0, (int) results.getSubstituteCount());
        assertEquals(0, (int) results.getDistance());
    }
}
