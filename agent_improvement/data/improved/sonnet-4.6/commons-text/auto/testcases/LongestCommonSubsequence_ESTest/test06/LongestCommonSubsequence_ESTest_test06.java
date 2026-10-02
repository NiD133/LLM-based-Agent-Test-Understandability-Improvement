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
    public void test06_lcsLengthIsZeroWhenRightSequenceIsEmpty() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;
        // Left sequence: 7-character buffer (filled with null chars)
        CharBuffer nonEmptyLeft = CharBuffer.allocate(7);
        // Right sequence: empty (wrapping an empty char array)
        CharBuffer emptyRight = CharBuffer.wrap(new char[0]);

        Integer result = lcs.apply((CharSequence) nonEmptyLeft, (CharSequence) emptyRight);

        assertEquals(0, (int) result);
    }
}
