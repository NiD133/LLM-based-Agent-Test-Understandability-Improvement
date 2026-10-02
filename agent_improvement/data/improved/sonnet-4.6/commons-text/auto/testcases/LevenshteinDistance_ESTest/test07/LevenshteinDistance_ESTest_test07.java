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
public class LevenshteinDistance_ESTest_test07 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing a string to itself returns a Levenshtein distance of 0,
     * even when the distance threshold is set to Integer.MAX_VALUE.
     * Identical strings require no edits, so the result must always be 0.
     */
    @Test(timeout = 4000)
    public void test_identicalStrings_returnZeroDistance_withMaxThreshold() throws Throwable {
        Integer maxThreshold = new Integer(Integer.MAX_VALUE);
        LevenshteinDistance distanceWithMaxThreshold = new LevenshteinDistance(maxThreshold);

        String input = "3{sMd^PVa";
        Integer distance = distanceWithMaxThreshold.apply((CharSequence) input, (CharSequence) input);

        assertEquals(0, (int) distance);
    }
}
