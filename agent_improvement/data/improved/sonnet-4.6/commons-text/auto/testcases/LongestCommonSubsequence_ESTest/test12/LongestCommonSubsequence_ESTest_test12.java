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
public class LongestCommonSubsequence_ESTest_test12 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * When the left sequence contains only null characters ('\0') and the right sequence
     * contains only printable characters, there are no characters in common, so the
     * longest common subsequence should be the empty string.
     */
    @Test(timeout = 4000)
    public void test_longestCommonSubsequence_noSharedCharacters_returnsEmpty() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // CharBuffer wrapping a 7-element array of null chars ('\0', '\0', ..., '\0')
        char[] nullChars = new char[7];
        CharBuffer leftAllNulls = CharBuffer.wrap(nullChars);

        // Right side has no null characters, so no character is shared with leftAllNulls
        CharSequence result = lcs.longestCommonSubsequence(leftAllNulls, "+*QLyOuk");

        assertEquals("", result);
    }
}
