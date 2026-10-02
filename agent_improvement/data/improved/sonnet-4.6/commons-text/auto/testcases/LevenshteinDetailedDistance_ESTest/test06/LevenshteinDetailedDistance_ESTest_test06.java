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
public class LevenshteinDetailedDistance_ESTest_test06 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing a CharBuffer to itself with a threshold of 0
     * yields a Levenshtein distance of 0 (identical sequences require no edits).
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        int threshold = 0;
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        CharBuffer buffer = CharBuffer.allocate(24);
        LevenshteinResults results = distance.apply((CharSequence) buffer, (CharSequence) buffer);

        assertEquals(0, (int) results.getDistance());
    }
}
