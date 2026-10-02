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
public class LevenshteinDistance_ESTest_test10 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final Integer threshold = Integer.valueOf(1426);
        final LevenshteinDistance distance = new LevenshteinDistance(threshold);
        final CharBuffer sourceWithThresholdLength = CharBuffer.allocate(1426);

        final Integer result = distance.apply((CharSequence) sourceWithThresholdLength, (CharSequence) "");

        assertEquals(1426, (int) result);
    }
}
