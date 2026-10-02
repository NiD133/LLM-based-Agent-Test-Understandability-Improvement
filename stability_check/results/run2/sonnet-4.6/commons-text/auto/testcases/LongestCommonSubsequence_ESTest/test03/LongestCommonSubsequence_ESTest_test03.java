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
public class LongestCommonSubsequence_ESTest_test03 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * Verifies that longestCommonSubsequence throws IllegalArgumentException
     * when the right (second) CharSequence argument is null, even when the
     * left argument is a valid non-empty CharBuffer.
     */
    @Test(timeout = 4000)
    public void test_longestCommonSubsequence_throwsIllegalArgumentException_whenRightInputIsNull() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // Create a valid 10-character CharBuffer as the left input
        char[] tenNullChars = new char[10];
        CharBuffer validLeftInput = CharBuffer.wrap(tenNullChars);

        // Passing null as the right input should trigger an IllegalArgumentException
        try {
            lcs.longestCommonSubsequence(validLeftInput, (CharSequence) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Inputs must not be null
            //
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
