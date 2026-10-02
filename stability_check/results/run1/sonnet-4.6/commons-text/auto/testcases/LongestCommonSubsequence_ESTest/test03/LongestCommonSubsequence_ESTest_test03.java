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
     * Verifies that passing a null right argument to longestCommonSubsequence throws
     * an IllegalArgumentException with the message "Inputs must not be null".
     * The left argument is a valid 10-character CharBuffer (all null chars).
     */
    @Test(timeout = 4000)
    public void test_longestCommonSubsequence_throwsWhenRightArgumentIsNull() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // Wrap a 10-element char array as the valid left CharSequence argument
        char[] tenNullChars = new char[10];
        CharBuffer leftSequence = CharBuffer.wrap(tenNullChars);

        // Calling with a null right argument must throw IllegalArgumentException
        try {
            lcs.longestCommonSubsequence(leftSequence, (CharSequence) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Inputs must not be null
            //
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
