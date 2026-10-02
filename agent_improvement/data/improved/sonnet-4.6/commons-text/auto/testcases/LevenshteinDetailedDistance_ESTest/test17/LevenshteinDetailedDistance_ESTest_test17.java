package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test17 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing two CharBuffer sequences of different lengths
     * where three positions differ by substitution produces deleteCount=0 and
     * substituteCount=3.
     *
     * Left  (7 chars): "\0\0\0S\0\0\0"   — 'S' at index 3
     * Right (8 chars): "\0S\0f\0S\0\0"   — 'S' at index 1, 'f' at index 3, 'S' at index 5
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        // Left sequence: 7-char buffer with 'S' at position 3
        char[] leftChars = new char[7];
        leftChars[3] = 'S';
        CharBuffer left = CharBuffer.wrap(leftChars);

        // Right sequence: 8-char buffer with 'S' at 1, 'f' at 3, 'S' at 5
        char[] rightChars = new char[8];
        rightChars[1] = 'S';
        rightChars[3] = 'f';
        rightChars[5] = 'S';
        CharBuffer right = CharBuffer.wrap(rightChars);

        LevenshteinResults results = distance.apply((CharSequence) left, (CharSequence) right);

        assertEquals(0, (int) results.getDeleteCount());
        assertEquals(3, (int) results.getSubstituteCount());
    }
}
