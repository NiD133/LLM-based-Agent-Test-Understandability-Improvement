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
     * Verifies that {@link LongestCommonSubsequence#longestCommonSubsequence(CharSequence, CharSequence)}
     * rejects a null argument by throwing an {@link IllegalArgumentException}, even when the other
     * argument is a valid (non-null) sequence.
     */
    @Test(timeout = 4000)
    public void longestCommonSubsequenceWithNullRightThrowsIllegalArgumentException() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        CharSequence nonNullLeft = CharBuffer.wrap(new char[10]);

        try {
            lcs.longestCommonSubsequence(nonNullLeft, (CharSequence) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The class rejects null inputs with the message "Inputs must not be null".
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
