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
public class DamerauLevenshteinDistance_ESTest_test05 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * When a zero threshold is configured, any pair of inputs that are not already
     * equal exceeds the threshold, so {@code apply} returns -1 instead of the real distance.
     *
     * Here the two single-character inputs differ (an empty/default '\0' character
     * versus '%'), giving an actual distance of 1, which is greater than the
     * threshold of 0 and therefore reported as -1.
     */
    @Test(timeout = 4000)
    public void thresholdOfZeroReturnsMinusOneWhenInputsDiffer() throws Throwable {
        Integer zeroThreshold = 0;
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(zeroThreshold);

        // Length-1 buffer holding the default '\0' character.
        CharBuffer defaultCharInput = CharBuffer.allocate(1);
        // Length-1 buffer holding the '%' character.
        CharBuffer percentCharInput = CharBuffer.wrap(new char[] { '%' });

        Integer result = distance.apply((CharSequence) defaultCharInput, (CharSequence) percentCharInput);

        assertEquals(-1, (int) result);
    }
}
