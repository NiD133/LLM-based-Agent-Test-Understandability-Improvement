package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test02 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * When one input has 116 characters and the other is empty, the
     * Damerau-Levenshtein distance equals the length of the non-empty input,
     * because every character must be deleted to turn it into the empty sequence.
     */
    @Test(timeout = 4000)
    public void distanceFromFullSequenceToEmptySequenceEqualsLength() throws Throwable {
        final int sequenceLength = 116;

        // A buffer of 116 characters: this is the non-empty input.
        CharBuffer fullSequence = CharBuffer.allocate(sequenceLength);

        // Wrap the same buffer but with an empty window (start == end == 116),
        // producing a CharSequence of length 0: this is the empty input.
        CharBuffer emptySequence = CharBuffer.wrap((CharSequence) fullSequence, sequenceLength, sequenceLength);

        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();

        Integer result = distance.apply((CharSequence) fullSequence, (CharSequence) emptySequence);

        assertEquals(sequenceLength, (int) result);
    }
}
