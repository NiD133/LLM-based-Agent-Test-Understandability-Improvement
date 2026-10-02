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
        LevenshteinDistance defaultDistance = LevenshteinDistance.getDefaultInstance();
        char[] twoNullCharacters = new char[2];
        CharBuffer repeatedBuffer = CharBuffer.wrap(twoNullCharacters);

        Integer distanceToSelf = defaultDistance.apply((CharSequence) repeatedBuffer, (CharSequence) repeatedBuffer);
        assertEquals(0, (int) distanceToSelf);

        LevenshteinDistance zeroThresholdDistance = new LevenshteinDistance(distanceToSelf);
        Integer thresholdedDistanceToSelf = zeroThresholdDistance.apply((CharSequence) repeatedBuffer, (CharSequence) repeatedBuffer);
        assertEquals(0, (int) thresholdedDistanceToSelf);
    }
}
