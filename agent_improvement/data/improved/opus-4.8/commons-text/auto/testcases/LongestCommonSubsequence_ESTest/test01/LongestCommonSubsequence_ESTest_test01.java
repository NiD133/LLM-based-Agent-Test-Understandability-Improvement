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
     * When one of the inputs is empty, the longest common subsequence is empty:
     * there are no characters that the two sequences can share.
     */
    @Test(timeout = 4000)
    public void longestCommonSubsequenceWithEmptyRightReturnsEmpty() throws Throwable {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        // A non-empty left input (four NUL characters) paired with an empty right input.
        CharBuffer left = CharBuffer.wrap(new char[4]);
        String right = "";

        CharSequence result = lcs.longestCommonSubsequence(left, right);

        assertEquals("", result);
    }
}
