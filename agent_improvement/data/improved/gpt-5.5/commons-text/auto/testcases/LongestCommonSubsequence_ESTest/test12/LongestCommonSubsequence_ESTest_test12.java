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

    @Test(timeout = 4000)
    public void returnsEmptySubsequenceForZeroFilledBufferAndPrintableText() throws Throwable {
        final LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        final char[] zeroFilledCharacters = new char[7];
        final CharBuffer zeroFilledBuffer = CharBuffer.wrap(zeroFilledCharacters);

        final CharSequence subsequence = longestCommonSubsequence.longestCommonSubsequence(zeroFilledBuffer, "+*QLyOuk");

        assertEquals("", subsequence);
    }
}
