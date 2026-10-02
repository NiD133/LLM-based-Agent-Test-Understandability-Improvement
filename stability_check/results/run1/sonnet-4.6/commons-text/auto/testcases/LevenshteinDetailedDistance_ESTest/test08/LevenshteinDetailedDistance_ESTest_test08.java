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
     * Verifies that comparing a CharBuffer sequence against itself yields a distance of zero,
     * even when using a very large threshold (Integer.MAX_VALUE).
     * Two-character null-char CharBuffers are identical, so no edits are required.
     */
    @Test(timeout = 4000)
    public void test_identicalCharBufferInputsHaveZeroDistance() throws Throwable {
        // A two-character CharBuffer whose characters are both '\0' (default)
        char[] twoNullChars = new char[2];
        CharBuffer identicalInput = CharBuffer.wrap(twoNullChars);

        // Use a very high threshold so the limited comparison path is taken but never blocks any result
        Integer unlimitedThreshold = new Integer(Integer.MAX_VALUE);
        LevenshteinDetailedDistance distanceCalculator = new LevenshteinDetailedDistance(unlimitedThreshold);

        // Comparing a sequence to itself must always report zero edits needed
        LevenshteinResults results = distanceCalculator.apply((CharSequence) identicalInput, (CharSequence) identicalInput);
        assertEquals(0, (int) results.getDistance());
    }
}
