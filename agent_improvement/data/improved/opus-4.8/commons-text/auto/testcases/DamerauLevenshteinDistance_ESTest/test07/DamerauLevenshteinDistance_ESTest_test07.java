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
public class DamerauLevenshteinDistance_ESTest_test07 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * When one input has the given length and the other is empty, the distance equals
     * the length of the non-empty input (every character must be inserted/deleted).
     *
     * <p>Here the threshold equals that length, so the result is not clamped to -1.</p>
     */
    @Test(timeout = 4000)
    public void distanceOfNonEmptyVersusEmptyEqualsNonEmptyLength() throws Throwable {
        final int sequenceLength = 582;
        final int threshold = 582;
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(threshold);

        // A buffer of 582 characters compared against an empty sub-sequence of itself.
        CharBuffer nonEmptyInput = CharBuffer.allocate(sequenceLength);
        CharBuffer emptyInput = CharBuffer.wrap((CharSequence) nonEmptyInput, sequenceLength, sequenceLength);

        Integer result = distance.apply((CharSequence) nonEmptyInput, (CharSequence) emptyInput);

        assertEquals(sequenceLength, (int) result);
    }
}
