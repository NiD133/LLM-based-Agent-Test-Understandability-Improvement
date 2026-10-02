package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test08 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Comparing a CharBuffer against itself with the default (unlimited) instance should yield distance 0
        LevenshteinDistance defaultInstance = LevenshteinDistance.getDefaultInstance();
        char[] fiveNullChars = new char[5];
        CharBuffer shortBuffer = CharBuffer.wrap(fiveNullChars);
        Integer distanceSameString = defaultInstance.apply((CharSequence) shortBuffer, (CharSequence) shortBuffer);
        assertEquals(0, (int) distanceSameString);

        // Reuse the distance (0) as the threshold; comparing the short buffer against a much longer one
        // exceeds the threshold of 0, so the result is -1 (distance not within threshold)
        LevenshteinDistance thresholdZeroInstance = new LevenshteinDistance(distanceSameString);
        CharBuffer longBuffer = CharBuffer.allocate(3767);
        Integer distanceExceedsThreshold = thresholdZeroInstance.apply((CharSequence) shortBuffer, (CharSequence) longBuffer);
        assertEquals((-1), (int) distanceExceedsThreshold);
    }
}
