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

    /**
     * When one of the inputs is empty, the two sequences share no characters,
     * so the longest common subsequence length must be 0.
     */
    @Test(timeout = 4000)
    public void applyWithEmptyRightSequenceReturnsZero() throws Throwable {
        LongestCommonSubsequence lcs = LongestCommonSubsequence.INSTANCE;

        // A buffer of blanks (length 7) vs. an empty buffer (length 0).
        CharBuffer nonEmptyLeft = CharBuffer.allocate(7);
        CharBuffer emptyRight = CharBuffer.wrap(new char[0]);

        Integer lcsLength = lcs.apply((CharSequence) nonEmptyLeft, (CharSequence) emptyRight);

        assertEquals(0, (int) lcsLength);
    }
}
