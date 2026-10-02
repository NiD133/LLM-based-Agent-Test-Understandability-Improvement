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
        final LongestCommonSubsequence longestCommonSubsequence = LongestCommonSubsequence.INSTANCE;
        final CharBuffer leftSequence = CharBuffer.allocate(7);
        final char[] emptyCharacters = new char[0];
        final CharBuffer rightSequence = CharBuffer.wrap(emptyCharacters);

        final Integer commonSubsequenceLength = longestCommonSubsequence.apply(
                (CharSequence) leftSequence,
                (CharSequence) rightSequence);

        assertEquals(0, (int) commonSubsequenceLength);
    }
}
