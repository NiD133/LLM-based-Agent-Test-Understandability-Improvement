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
     * When a threshold is set, apply() returns -1 if the true distance exceeds it.
     * Here the threshold is 0, the left input is empty, and the right input has 16
     * characters. Transforming an empty sequence into a 16-character one costs 16
     * insertions, which is greater than the threshold of 0, so the result is -1.
     */
    @Test(timeout = 4000)
    public void distanceExceedingThresholdReturnsMinusOne() throws Throwable {
        DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(0);

        CharSequence emptyLeft = CharBuffer.allocate(0);
        CharSequence sixteenCharRight = "DN{S5$O|Wi*p/ZbT";
        Integer distance = distanceWithZeroThreshold.apply(emptyLeft, sixteenCharRight);

        assertEquals(-1, (int) distance);
    }
}
