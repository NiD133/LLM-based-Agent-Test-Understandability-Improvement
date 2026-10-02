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
public class LongestCommonSubsequence_ESTest_test07 extends LongestCommonSubsequence_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;
        char[] emptyCharacters = new char[0];
        CharBuffer emptySequence = CharBuffer.wrap(emptyCharacters);

        Integer commonSubsequenceLength = lcs.apply((CharSequence) emptySequence, (CharSequence) emptySequence);

        assertEquals(0, (int) commonSubsequenceLength);
    }
}
