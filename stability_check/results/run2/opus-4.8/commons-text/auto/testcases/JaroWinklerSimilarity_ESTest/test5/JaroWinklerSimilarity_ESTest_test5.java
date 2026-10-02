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
public class JaroWinklerSimilarity_ESTest_test5 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies {@link JaroWinklerSimilarity#matches} when the two inputs share
     * the same backing char array but expose different remaining regions.
     *
     * <p>A 12-char array (all NUL '\0') is wrapped twice. Writing 9 characters
     * into the first buffer advances its position to 9, so as a CharSequence it
     * only exposes the 3 remaining (still NUL) characters, while the second
     * buffer still exposes all 12 NUL characters. Because the shorter input's
     * NUL characters have no matching counterpart in the compared window, the
     * algorithm reports zero matches, zero half-transpositions and zero
     * prefix.</p>
     */
    @Test(timeout = 4000)
    public void matchesReturnsAllZerosWhenNoCharactersAlign() throws Throwable {
        // Shared 12-element char array, default-initialised to NUL characters.
        char[] sharedChars = new char[12];

        // First buffer: writing 9 chars advances its position, leaving only
        // the last 3 (NUL) characters visible via the CharSequence view.
        CharBuffer advancedBuffer = CharBuffer.wrap(sharedChars);
        advancedBuffer.put("X19l5(mgJ");

        // Second buffer: fresh view over the same array, exposing all 12 chars.
        CharBuffer fullBuffer = CharBuffer.wrap(sharedChars);

        int[] result = JaroWinklerSimilarity.matches(fullBuffer, advancedBuffer);

        // Expected: { matches, halfTranspositions, prefix } = { 0, 0, 0 }.
        assertArrayEquals(new int[] { 0, 0, 0 }, result);
    }
}
