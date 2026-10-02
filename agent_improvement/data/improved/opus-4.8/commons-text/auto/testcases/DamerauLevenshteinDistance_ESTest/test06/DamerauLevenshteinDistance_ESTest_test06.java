package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test06 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * When the two inputs differ in length by more than the configured threshold,
     * {@code apply} returns -1 because the distance can never fall within the threshold.
     *
     * Here the threshold is 15, but the inputs have lengths 15 and 332, a length
     * difference of 317, so the result must be the "exceeded threshold" sentinel -1.
     */
    @Test(timeout = 4000)
    public void thresholdExceededByLengthDifferenceReturnsMinusOne() throws Throwable {
        final int threshold = 15;
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(threshold);

        CharBuffer shortInput = CharBuffer.allocate(15);
        CharBuffer longInput = CharBuffer.allocate(332);

        Integer result = distance.apply((CharSequence) shortInput, (CharSequence) longInput);

        assertEquals(-1, (int) result);
    }
}
