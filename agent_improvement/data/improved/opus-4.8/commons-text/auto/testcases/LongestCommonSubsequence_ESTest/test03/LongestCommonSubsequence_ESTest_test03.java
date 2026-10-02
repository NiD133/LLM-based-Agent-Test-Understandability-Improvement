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
     * rejects a null input by throwing an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void longestCommonSubsequence_withNullRightInput_throwsIllegalArgumentException() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        CharSequence nonNullLeft = CharBuffer.wrap(new char[10]);

        try {
            lcs.longestCommonSubsequence(nonNullLeft, (CharSequence) null);
            fail("Expected IllegalArgumentException because the right input is null");
        } catch (IllegalArgumentException e) {
            // The CUT rejects null inputs with the message "Inputs must not be null".
            verifyException("org.apache.commons.text.similarity.LongestCommonSubsequence", e);
        }
    }
}
