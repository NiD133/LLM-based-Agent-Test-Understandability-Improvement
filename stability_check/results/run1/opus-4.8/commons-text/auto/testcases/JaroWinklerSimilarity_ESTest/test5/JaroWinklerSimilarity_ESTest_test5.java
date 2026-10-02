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
     * Verifies {@link JaroWinklerSimilarity#matches(CharSequence, CharSequence)} when the two
     * inputs are two {@link CharBuffer} views over the same backing array but with different
     * effective lengths.
     *
     * <p>Both buffers wrap the same 12-element array. Writing 9 characters into the first buffer
     * advances its position to 9, so as a {@link CharSequence} it now exposes only its remaining
     * 3 elements, while the freshly wrapped second buffer still exposes all 12. Because the buffers
     * expose different windows of the array, no characters line up: {@code matches} reports zero
     * matches, zero half-transpositions and zero common prefix.</p>
     */
    @Test(timeout = 4000)
    public void matchesReturnsAllZerosForMisalignedCharBufferWindows() throws Throwable {
        final char[] sharedContents = new char[12];

        // Fill the first 9 slots; this also advances the buffer's position to 9, shrinking the
        // length it reports as a CharSequence from 12 down to the 3 remaining slots.
        final CharBuffer partiallyWrittenBuffer = CharBuffer.wrap(sharedContents);
        partiallyWrittenBuffer.put("X19l5(mgJ");

        // A second view over the same array, still positioned at 0, so it reports a length of 12.
        final CharBuffer fullLengthBuffer = CharBuffer.wrap(sharedContents);

        final int[] matchStats = JaroWinklerSimilarity.matches(fullLengthBuffer, partiallyWrittenBuffer);

        // { matches, half-transpositions, prefix } are all zero.
        assertArrayEquals(new int[] { 0, 0, 0 }, matchStats);
    }
}
