package org.apache.commons.text.similarity;

import static org.junit.Assert.assertArrayEquals;

import java.nio.CharBuffer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test5 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies {@link JaroWinklerSimilarity#matches(CharSequence, CharSequence)} for two
     * {@link CharBuffer}s that share the same backing array but read from different positions.
     *
     * <p>Both buffers wrap the same 12-char array. Writing "X19l5(mgJ" through {@code writtenView}
     * fills array slots 0..8 and advances that buffer's position to 9, so as a {@link CharSequence}
     * it now exposes only the 3 trailing slots (still the default '\0'). {@code fullView} keeps
     * position 0 and therefore exposes all 12 chars.</p>
     *
     * <p>The 3 trailing '\0' characters from {@code writtenView} never line up with any character
     * in {@code fullView}, so the algorithm reports zero matches, zero half-transpositions and a
     * zero common prefix: {@code {0, 0, 0}}.</p>
     */
    @Test(timeout = 4000)
    public void matchesReturnsAllZerosWhenNoCharactersAlign() throws Throwable {
        final char[] sharedBackingArray = new char[12];

        // Write 9 characters through one view: fills slots 0..8 and moves its position to 9.
        final CharBuffer writtenView = CharBuffer.wrap(sharedBackingArray);
        writtenView.put("X19l5(mgJ");

        // A second view of the same array, still positioned at 0, so it exposes all 12 chars.
        final CharBuffer fullView = CharBuffer.wrap(sharedBackingArray);

        final int[] matchesTranspositionsPrefix = JaroWinklerSimilarity.matches(fullView, writtenView);

        assertArrayEquals(new int[] { 0, 0, 0 }, matchesTranspositionsPrefix);
    }
}
