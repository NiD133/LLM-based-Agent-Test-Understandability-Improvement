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
public class LongestCommonSubsequence_ESTest_test05 extends LongestCommonSubsequence_ESTest_scaffolding {

    private static final String TEXT_WITH_LEADING_NUL = "\u0000r";
    private static final int ZERO_FILLED_BUFFER_LENGTH = 3226;
    private static final int EXPECTED_COMMON_SUBSEQUENCE_LENGTH = 1;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        CharBuffer zeroFilledBuffer = CharBuffer.allocate(ZERO_FILLED_BUFFER_LENGTH);

        Integer commonSubsequenceLength = longestCommonSubsequence.apply(
                (CharSequence) TEXT_WITH_LEADING_NUL,
                (CharSequence) zeroFilledBuffer);

        assertEquals(EXPECTED_COMMON_SUBSEQUENCE_LENGTH, (int) commonSubsequenceLength);
    }
}
