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
public class LongestCommonSubsequence_ESTest_test01 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * When the right operand is an empty string, longestCommonSubsequence must
     * return an empty string regardless of what the left operand contains.
     *
     * The left sequence is a CharBuffer wrapping four null characters ('\0');
     * no character in it can match anything in the empty right sequence, so
     * the LCS is "".
     */
    @Test(timeout = 4000)
    public void test01_lcsWithEmptyRightSequenceReturnsEmpty() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // Build a non-empty left sequence: a CharBuffer of four null characters.
        char[] fourNullChars = new char[4];
        CharBuffer leftSequence = CharBuffer.wrap(fourNullChars);

        String emptyRightSequence = "";

        CharSequence result = lcs.longestCommonSubsequence(leftSequence, emptyRightSequence);

        assertEquals("LCS against an empty sequence must be empty", "", result);
    }
}
