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

    private static final String EMPTY_SEQUENCE = "";
    private static final int LEFT_SEQUENCE_LENGTH = 4;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        char[] leftCharacters = new char[LEFT_SEQUENCE_LENGTH];
        CharBuffer leftSequence = CharBuffer.wrap(leftCharacters);

        CharSequence commonSubsequence = longestCommonSubsequence.longestCommonSubsequence(leftSequence, EMPTY_SEQUENCE);

        assertEquals(EMPTY_SEQUENCE, commonSubsequence);
    }
}
