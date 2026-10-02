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
public class LongestCommonSubsequence_ESTest_test09 extends LongestCommonSubsequence_ESTest_scaffolding {

    private static final String NON_NULL_LEFT_SEQUENCE = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
    private static final CharSequence NULL_RIGHT_SEQUENCE = null;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        LongestCommonSubsequence longestCommonSubsequence0 = LongestCommonSubsequence.INSTANCE;

        try {
            longestCommonSubsequence0.apply((CharSequence) NON_NULL_LEFT_SEQUENCE, (CharSequence) NULL_RIGHT_SEQUENCE);
            fail("Expected null right input to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
