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
public class LevenshteinDistance_ESTest_test11 extends LevenshteinDistance_ESTest_scaffolding {

    // Sentinel returned by LevenshteinDistance.apply() when the edit distance exceeds the threshold
    private static final int DISTANCE_EXCEEDS_THRESHOLD = -1;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Configure a zero threshold: only identical strings (distance == 0) return a valid result;
        // any non-zero distance causes apply() to return -1
        Integer zeroThreshold = new Integer(0);
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(zeroThreshold);

        // An empty CharBuffer (capacity 0) represents an empty CharSequence
        CharBuffer emptyTarget = CharBuffer.allocate(0);

        // Source string has length 28; distance to empty string is 28, which exceeds threshold 0,
        // so the expected result is -1
        Integer result = distanceWithZeroThreshold.apply(
                (CharSequence) "or.apace.commons.tex.StrLokp",
                (CharSequence) emptyTarget);

        assertEquals(DISTANCE_EXCEEDS_THRESHOLD, (int) result);
    }
}
