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
     * Verifies that when the left input is empty and the right input exceeds the threshold,
     * the distance is clamped to -1. Here threshold=0 and right has 16 characters, so
     * the result must be -1 (distance 16 > threshold 0).
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Integer threshold = Integer.valueOf(0);
        DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(threshold);

        // An empty CharBuffer represents a zero-length left input
        CharBuffer emptyLeft = CharBuffer.allocate(0);
        String sixteenCharRight = "DN{S5$O|Wi*p/ZbT";

        Integer distance = distanceWithZeroThreshold.apply((CharSequence) emptyLeft, (CharSequence) sixteenCharRight);

        // Empty left vs. 16-char right exceeds threshold 0, so result is clamped to -1
        assertEquals((-1), (int) distance);
    }
}
