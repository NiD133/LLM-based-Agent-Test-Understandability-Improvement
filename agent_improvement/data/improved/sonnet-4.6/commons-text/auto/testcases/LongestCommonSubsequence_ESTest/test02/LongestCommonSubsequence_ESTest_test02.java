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
public class LongestCommonSubsequence_ESTest_test02 extends LongestCommonSubsequence_ESTest_scaffolding {

    /**
     * Verifies that when both input sequences are empty, the longest common
     * subsequence is also empty. Uses the deprecated {@code logestCommonSubsequence}
     * method (note the typo in the method name) with an empty CharBuffer on the
     * left and an empty String on the right.
     */
    @Test(timeout = 4000)
    public void test_logestCommonSubsequence_bothInputsEmpty_returnsEmptyString() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // An empty CharBuffer represents a left sequence with no characters.
        CharBuffer emptyLeft = CharBuffer.allocate(0);
        String emptyRight = "";

        CharSequence result = lcs.logestCommonSubsequence(emptyLeft, emptyRight);

        assertEquals(
            "LCS of two empty sequences must be an empty string",
            "",
            result
        );
    }
}
