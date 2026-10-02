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
public class LevenshteinDistance_ESTest_test13 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * With a threshold of 0, the distance is returned only when it is 0 (i.e.
     * the two inputs are equal). Here an empty sequence is compared against a
     * 17-character string, so the real distance (17) exceeds the threshold and
     * the algorithm reports -1 to signal "above threshold".
     */
    @Test(timeout = 4000)
    public void apply_distanceAboveZeroThreshold_returnsMinusOne() throws Throwable {
        Integer threshold = 0;
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(threshold);

        CharSequence emptySequence = CharBuffer.allocate(0);
        CharSequence nonEmptySequence = "&D/r2c/;WL~$DxE.m";

        Integer distance = distanceWithZeroThreshold.apply(emptySequence, nonEmptySequence);

        assertEquals(-1, (int) distance);
    }
}
