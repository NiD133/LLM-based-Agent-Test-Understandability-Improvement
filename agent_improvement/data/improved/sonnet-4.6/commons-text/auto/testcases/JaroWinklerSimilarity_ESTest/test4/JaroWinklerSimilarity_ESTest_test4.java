package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test4 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies that matches() correctly computes [matchCount, halfTranspositions, commonPrefixLength]
     * when comparing two CharBuffers of different lengths that share characters at non-overlapping positions.
     *
     * shorter (first arg):  ['\0', '\0', '\0', 'f', '\0', '\0']  — length 6, 'f' at index 3
     * longer  (second arg): ['\0', 'f',  '\0', '\0', '\0', '\0', '\0', '\0']  — length 8, 'f' at index 1
     *
     * All 6 shorter-string characters match something in the longer string (within the Jaro window),
     * but the matched 'f' characters appear in reversed relative order → 2 half-transpositions.
     * The first character '\0' is shared at position 0 → common prefix length = 1.
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // longer buffer: length 8, only index 1 holds 'f'
        char[] longerChars = new char[8];
        longerChars[1] = 'f';
        CharBuffer longerBuffer = CharBuffer.wrap(longerChars);

        // shorter buffer: length 6, only index 3 holds 'f'
        char[] shorterChars = new char[6];
        shorterChars[3] = 'f';
        CharBuffer shorterBuffer = CharBuffer.wrap(shorterChars);

        // matches() returns int[] { matchCount, halfTranspositions, commonPrefixLength }
        int[] result = JaroWinklerSimilarity.matches(shorterBuffer, longerBuffer);

        assertArrayEquals(new int[] {6, 2, 1}, result);
    }
}
