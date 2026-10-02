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

    // When a CharBuffer of N null characters is compared against an empty string with threshold N,
    // the distance equals N (exactly N deletions needed, which is within the threshold).
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final int threshold = 1426;
        LevenshteinDistance distance = new LevenshteinDistance(threshold);
        CharBuffer sourceBuffer = CharBuffer.allocate(threshold);
        Integer result = distance.apply((CharSequence) sourceBuffer, (CharSequence) "");
        assertEquals(threshold, (int) result);
    }
}
