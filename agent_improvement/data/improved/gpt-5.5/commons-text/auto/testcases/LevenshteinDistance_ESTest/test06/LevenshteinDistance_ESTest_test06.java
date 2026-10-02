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
public class LevenshteinDistance_ESTest_test06 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        LevenshteinDistance defaultDistance = LevenshteinDistance.getDefaultInstance();

        char[] sourceCharacters = new char[2];
        sourceCharacters[0] = ')';
        CharBuffer sourceBuffer = CharBuffer.wrap(sourceCharacters);

        Integer zeroDistanceThreshold = defaultDistance.apply((CharSequence) sourceBuffer, (CharSequence) sourceBuffer);

        char[] targetCharacters = new char[2];
        CharBuffer targetBuffer = CharBuffer.wrap(targetCharacters);

        // A zero threshold cannot cover the one-character difference between these buffers.
        LevenshteinDistance thresholdLimitedDistance = new LevenshteinDistance(zeroDistanceThreshold);
        Integer distanceBeyondThreshold = thresholdLimitedDistance.apply((CharSequence) sourceBuffer, (CharSequence) targetBuffer);

        assertEquals((-1), (int) distanceBeyondThreshold);
    }
}
