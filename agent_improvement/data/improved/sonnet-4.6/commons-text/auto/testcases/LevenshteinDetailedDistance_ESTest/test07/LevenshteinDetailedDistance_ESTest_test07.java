package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test07 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the actual edit distance between the two inputs far exceeds the threshold,
     * the limited-distance algorithm returns -1 to indicate "threshold exceeded".
     *
     * left  = CharBuffer.allocate(1)  → one NUL character ('\0')
     * right = "y{Ux1x:{|e"           → 10 characters
     * threshold = 1
     *
     * Transforming a 1-char string into a 10-char string requires at least 9 edits,
     * which is well above the threshold of 1, so the expected distance is -1.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        CharBuffer singleNulChar = CharBuffer.allocate(1);
        int threshold = 1;
        LevenshteinDetailedDistance distanceWithThreshold = new LevenshteinDetailedDistance(threshold);

        LevenshteinResults result = distanceWithThreshold.apply((CharSequence) singleNulChar, (CharSequence) "y{Ux1x:{|e");

        assertEquals(-1, (int) result.getDistance());
    }
}
