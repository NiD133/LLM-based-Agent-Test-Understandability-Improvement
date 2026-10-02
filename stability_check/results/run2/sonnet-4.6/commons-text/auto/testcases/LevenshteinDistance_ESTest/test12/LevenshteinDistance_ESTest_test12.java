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
     * Verifies that comparing an empty CharBuffer against itself with threshold 0
     * returns a distance of 0 (two identical empty sequences have no edits needed).
     */
    @Test(timeout = 4000)
    public void test_emptyCharBufferComparedToItself_withZeroThreshold_returnsZero() throws Throwable {
        int threshold = 0;
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(threshold);

        CharBuffer emptyBuffer = CharBuffer.allocate(0);

        Integer distance = distanceWithZeroThreshold.apply((CharSequence) emptyBuffer, (CharSequence) emptyBuffer);

        assertEquals(0, (int) distance);
    }
}
