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
     * Verifies that when threshold is 0 and the left input is an empty CharBuffer while
     * the right input has 16 characters, the distance (16) exceeds the threshold,
     * so the method returns -1.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Threshold of 0: only exact matches (distance == 0) are within threshold
        Integer threshold = new Integer(0);
        DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(threshold);

        // Empty left input; distance to any non-empty string equals the right string's length
        CharBuffer emptyLeft = CharBuffer.allocate(0);
        String nonEmptyRight = "DN{S5$O|Wi*p/ZbT"; // 16 characters

        // Distance is 16 (all insertions needed), which exceeds threshold 0, so result is -1
        Integer result = distanceWithZeroThreshold.apply((CharSequence) emptyLeft, (CharSequence) nonEmptyRight);
        assertEquals((-1), (int) result);
    }
}
