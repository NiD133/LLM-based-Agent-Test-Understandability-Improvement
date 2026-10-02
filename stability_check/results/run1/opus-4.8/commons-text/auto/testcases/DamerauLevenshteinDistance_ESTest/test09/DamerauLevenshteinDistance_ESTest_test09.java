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
     * When the threshold is zero, transforming an empty sequence into a
     * non-empty one requires one insertion per character, so the distance
     * exceeds the threshold and {@code apply} must return -1.
     */
    @Test(timeout = 4000)
    public void distanceExceedingZeroThresholdReturnsMinusOne() throws Throwable {
        Integer zeroThreshold = Integer.valueOf(0);
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(zeroThreshold);

        CharSequence emptySequence = CharBuffer.allocate(0);
        CharSequence nonEmptySequence = "DN{S5$O|Wi*p/ZbT";

        Integer result = distance.apply(emptySequence, nonEmptySequence);

        assertEquals("Distance above the zero threshold should be reported as -1",
                -1, (int) result);
    }
}
