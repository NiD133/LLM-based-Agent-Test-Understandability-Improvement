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
     * With a threshold of 0, transforming an empty sequence into a 16-character
     * sequence would require 16 insertions, which exceeds the threshold. The
     * distance is therefore reported as -1 ("threshold exceeded").
     */
    @Test(timeout = 4000)
    public void distanceExceedingThresholdReturnsMinusOne() throws Throwable {
        DamerauLevenshteinDistance zeroThresholdDistance = new DamerauLevenshteinDistance(0);

        CharSequence emptySequence = CharBuffer.allocate(0);
        CharSequence sixteenCharSequence = "DN{S5$O|Wi*p/ZbT";

        Integer distance = zeroThresholdDistance.apply(emptySequence, sixteenCharSequence);

        assertEquals(-1, (int) distance);
    }
}
