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
public class DamerauLevenshteinDistance_ESTest_test09 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * When the threshold is 0, any non-empty target string compared against an empty source
     * produces a distance greater than the threshold, so the result must be -1.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Threshold of 0: only identical strings are within the allowed distance
        Integer threshold = new Integer(0);
        DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(threshold);

        // Empty source sequence (length 0) vs a 16-character target
        CharBuffer emptySource = CharBuffer.allocate(0);
        String nonEmptyTarget = "DN{S5$O|Wi*p/ZbT";

        // Distance equals the length of the non-empty string (16), which exceeds threshold 0 -> expect -1
        Integer result = distanceWithZeroThreshold.apply((CharSequence) emptySource, (CharSequence) nonEmptyTarget);
        assertEquals((-1), (int) result);
    }
}
