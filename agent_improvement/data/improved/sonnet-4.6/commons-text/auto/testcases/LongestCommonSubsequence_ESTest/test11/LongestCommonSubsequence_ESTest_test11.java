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
public class LongestCommonSubsequence_ESTest_test11 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * Verifies that logestCommonSubsequence (the deprecated typo-named variant) correctly finds
     * the LCS when the left input is a CharBuffer of two null characters (code point 0)
     * and the right input is a null character followed by the letter 'r'.
     *
     * Both sequences share exactly one null character in common, so the expected LCS is
     * a single null character.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // CharBuffer wrapping two null characters (char arrays are zero-initialised by default)
        char[] twoNullChars = new char[2];
        CharBuffer leftSequence = CharBuffer.wrap(twoNullChars);

        // Right sequence: null character (code point 0) followed by 'r'
        String nullChar = String.valueOf((char) 0);
        String rightSequence = nullChar + "r";

        // LCS of "\0\0" and "\0r" shares only the single leading null character
        CharSequence result = lcs.logestCommonSubsequence(leftSequence, rightSequence);

        assertEquals(nullChar, result);
    }
}
