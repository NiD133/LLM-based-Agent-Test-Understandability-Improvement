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
public class LevenshteinDistance_ESTest_test08 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LevenshteinDistance defaultDistance = LevenshteinDistance.getDefaultInstance();

        char[] sharedCharacters = new char[5];
        CharBuffer fiveCharacterBuffer = CharBuffer.wrap(sharedCharacters);

        Integer distanceToSameBuffer = defaultDistance.apply((CharSequence) fiveCharacterBuffer, (CharSequence) fiveCharacterBuffer);
        assertEquals(0, (int) distanceToSameBuffer);

        LevenshteinDistance zeroThresholdDistance = new LevenshteinDistance(distanceToSameBuffer);
        CharBuffer muchLongerBuffer = CharBuffer.allocate(3767);

        Integer distanceWithZeroThreshold = zeroThresholdDistance.apply((CharSequence) fiveCharacterBuffer, (CharSequence) muchLongerBuffer);
        assertEquals((-1), (int) distanceWithZeroThreshold);
    }
}
