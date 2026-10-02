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

    /**
     * When one input is empty, the Levenshtein distance equals the length of the
     * other input (every character must be inserted/deleted). Here the non-empty
     * input is a 1426-character buffer and the threshold equals that length, so
     * the limited algorithm returns the full distance of 1426 rather than -1.
     */
    @Test(timeout = 4000)
    public void distanceToEmptyStringEqualsLengthOfOtherInput() throws Throwable {
        int threshold = 1426;
        LevenshteinDistance distance = new LevenshteinDistance(Integer.valueOf(threshold));

        // A buffer of 1426 characters compared against the empty string.
        CharBuffer nonEmptyInput = CharBuffer.allocate(threshold);
        Integer result = distance.apply((CharSequence) nonEmptyInput, (CharSequence) "");

        assertEquals(1426, (int) result);
    }
}
