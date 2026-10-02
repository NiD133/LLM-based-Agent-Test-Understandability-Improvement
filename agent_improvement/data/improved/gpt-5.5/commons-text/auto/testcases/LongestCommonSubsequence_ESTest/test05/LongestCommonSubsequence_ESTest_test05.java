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
public class LongestCommonSubsequence_ESTest_test05 extends LongestCommonSubsequence_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        final CharBuffer nulCharacterBuffer = CharBuffer.allocate(3226);
        final CharSequence inputWithSharedNulCharacter = "\u0000r";

        final Integer longestCommonSubsequenceLength = longestCommonSubsequence.apply(
                inputWithSharedNulCharacter,
                nulCharacterBuffer);

        assertEquals(1, (int) longestCommonSubsequenceLength);
    }
}
