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
public class LevenshteinDistance_ESTest_test12 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing an empty CharBuffer against itself with a threshold of 0
     * yields a Levenshtein distance of 0 (identical empty sequences have no edits needed).
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Integer zeroThreshold = new Integer(0);
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(zeroThreshold);

        CharBuffer emptyBuffer = CharBuffer.allocate(0);
        Integer distance = distanceWithZeroThreshold.apply((CharSequence) emptyBuffer, (CharSequence) emptyBuffer);

        assertEquals(0, (int) distance);
    }
}
