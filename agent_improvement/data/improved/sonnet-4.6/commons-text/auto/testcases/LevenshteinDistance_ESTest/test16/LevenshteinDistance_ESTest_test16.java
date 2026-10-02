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
public class LevenshteinDistance_ESTest_test16 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // Build a CharBuffer containing two null characters ('\0', '\0')
        char[] twoNullChars = new char[2];
        CharBuffer identicalInput = CharBuffer.wrap(twoNullChars);

        // Default (unlimited) instance: distance between a string and itself must be 0
        LevenshteinDistance defaultInstance = LevenshteinDistance.getDefaultInstance();
        Integer distanceSameString = defaultInstance.apply((CharSequence) identicalInput, (CharSequence) identicalInput);
        assertEquals(0, (int) distanceSameString);

        // Threshold instance whose limit equals the distance just computed (0):
        // comparing a string to itself still yields 0 because 0 <= threshold 0
        LevenshteinDistance thresholdZeroInstance = new LevenshteinDistance(distanceSameString);
        Integer distanceSameStringWithThreshold = thresholdZeroInstance.apply((CharSequence) identicalInput, (CharSequence) identicalInput);
        assertEquals(0, (int) distanceSameStringWithThreshold);
    }
}
