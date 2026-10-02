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
public class LevenshteinDistance_ESTest_test09 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void thresholdedDistanceReturnsThresholdWhenInputsDifferByFortyOneCharacters() throws Throwable {
        Integer threshold = new Integer(41);
        LevenshteinDistance distance = new LevenshteinDistance(threshold);
        CharBuffer leftInput = CharBuffer.allocate(41);

        Integer actualDistance = distance.apply(
                (CharSequence) leftInput,
                (CharSequence) "org.apache.commons.text.StrLookup");

        assertEquals(41, (int) actualDistance);
    }
}
