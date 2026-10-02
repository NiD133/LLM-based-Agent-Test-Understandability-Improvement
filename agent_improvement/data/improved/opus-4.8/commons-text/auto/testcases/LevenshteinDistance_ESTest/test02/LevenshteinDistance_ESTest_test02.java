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
public class LevenshteinDistance_ESTest_test02 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * When one input sequence is empty, the Levenshtein distance equals the
     * length of the other sequence (every remaining character must be inserted).
     *
     * Here the first input is a 305-character buffer and the second input is an
     * empty sub-buffer (wrapped over the same backing buffer with an empty
     * range), so the expected distance is 305.
     */
    @Test(timeout = 4000)
    public void distanceOfNonEmptyAndEmptySequenceEqualsNonEmptyLength() throws Throwable {
        final int nonEmptyLength = 305;

        CharBuffer nonEmptySequence = CharBuffer.allocate(nonEmptyLength);
        // Wrap an empty range [305, 305) over the backing buffer to obtain a length-0 sequence.
        CharBuffer emptySequence = CharBuffer.wrap((CharSequence) nonEmptySequence, nonEmptyLength, nonEmptyLength);

        LevenshteinDistance defaultDistance = new LevenshteinDistance();
        Integer distance = defaultDistance.apply((CharSequence) nonEmptySequence, (CharSequence) emptySequence);

        assertEquals(nonEmptyLength, (int) distance);
    }
}
