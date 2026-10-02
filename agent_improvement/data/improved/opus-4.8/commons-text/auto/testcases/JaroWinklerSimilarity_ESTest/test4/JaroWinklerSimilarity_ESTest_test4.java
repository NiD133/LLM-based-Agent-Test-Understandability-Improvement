package org.apache.commons.text.similarity;

import static org.junit.Assert.assertArrayEquals;

import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test4 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies the low-level {@link JaroWinklerSimilarity#matches} helper, which returns
     * {@code [matches, halfTranspositions, prefix]} for two character sequences.
     *
     * <p>Both inputs are mostly NUL ('\0') characters with a single 'f' placed at
     * different positions:</p>
     * <ul>
     *   <li>{@code first}  (length 6): five '\0' and an 'f' at index 3</li>
     *   <li>{@code second} (length 8): seven '\0' and an 'f' at index 1</li>
     * </ul>
     *
     * <p>Because the two sequences share many '\0' characters (and the single 'f'),
     * the helper reports 6 matched characters, 2 half-transpositions, and a common
     * prefix length of 1 (the leading '\0').</p>
     */
    @Test(timeout = 4000)
    public void matchesCountsSharedCharsTranspositionsAndPrefix() throws Throwable {
        // first: "______f__" style buffer of length 6 with 'f' at index 3
        final char[] firstChars = new char[6];
        firstChars[3] = 'f';
        final CharBuffer first = CharBuffer.wrap(firstChars);

        // second: buffer of length 8 with 'f' at index 1
        final char[] secondChars = new char[8];
        secondChars[1] = 'f';
        final CharBuffer second = CharBuffer.wrap(secondChars);

        final int[] matchesTranspositionsPrefix = JaroWinklerSimilarity.matches(first, second);

        // [matches, halfTranspositions, prefix]
        assertArrayEquals(new int[] { 6, 2, 1 }, matchesTranspositionsPrefix);
    }
}
