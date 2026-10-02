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
public class LongestCommonSubsequence_ESTest_test06 extends LongestCommonSubsequence_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        LongestCommonSubsequence longestCommonSubsequence0 = LongestCommonSubsequence.INSTANCE;
        CharBuffer charBuffer0 = CharBuffer.allocate(7);
        char[] charArray0 = new char[0];
        CharBuffer charBuffer1 = CharBuffer.wrap(charArray0);
        Integer integer0 = longestCommonSubsequence0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer1);
        assertEquals(0, (int) integer0);
    }
}
