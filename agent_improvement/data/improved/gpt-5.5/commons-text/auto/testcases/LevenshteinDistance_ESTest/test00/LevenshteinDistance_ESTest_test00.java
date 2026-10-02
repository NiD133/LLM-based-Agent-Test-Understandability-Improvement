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
public class LevenshteinDistance_ESTest_test00 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        char[] eightNullCharacters = new char[8];
        CharBuffer leftInput = CharBuffer.wrap(eightNullCharacters);
        Integer threshold = new Integer(13);
        LevenshteinDistance boundedDistance = new LevenshteinDistance(threshold);

        Integer distanceBeyondThreshold = boundedDistance.apply((CharSequence) leftInput, (CharSequence) "d0tT~H04{j;2W=");

        LevenshteinDistance distanceWithInvalidThreshold = null;
        try {
            distanceWithInvalidThreshold = new LevenshteinDistance(distanceBeyondThreshold);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Threshold must not be negative
            //
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}
