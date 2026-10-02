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
     * Verifies that applying a threshold-limited LevenshteinDistance to two identical
     * empty CharBuffers returns a distance of 0 (two empty sequences are identical).
     */
    @Test(timeout = 4000)
    public void test12_emptyCharBuffersHaveZeroDistance() throws Throwable {
        int threshold = 0;
        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(Integer.valueOf(threshold));
        CharBuffer emptyBuffer = CharBuffer.allocate(0);

        Integer distance = distanceWithThreshold.apply((CharSequence) emptyBuffer, (CharSequence) emptyBuffer);

        assertEquals(0, (int) distance);
    }
}
