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
public class LongestCommonSubsequence_ESTest_test08 extends LongestCommonSubsequence_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LongestCommonSubsequence longestCommonSubsequence0 = new LongestCommonSubsequence();
        char[] charArray0 = new char[2];
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray0);
        Integer integer0 = longestCommonSubsequence0.apply((CharSequence) "\u0000r", (CharSequence) charBuffer0);
        assertEquals(1, (int) integer0);
    }
}
