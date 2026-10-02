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
public class LevenshteinDistance_ESTest_test08 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies two behaviours:
     * <ol>
     *   <li>The default (unlimited) instance reports a distance of 0 between a
     *       sequence and itself.</li>
     *   <li>A threshold-limited instance returns -1 when the two sequences differ
     *       in length by more than the threshold allows.</li>
     * </ol>
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // The default instance has no threshold, so it computes the exact distance.
        LevenshteinDistance unlimitedDistance = LevenshteinDistance.getDefaultInstance();

        // A 5-character sequence compared against itself: no edits are needed.
        CharBuffer fiveChars = CharBuffer.wrap(new char[5]);
        Integer distanceToSelf = unlimitedDistance.apply((CharSequence) fiveChars, (CharSequence) fiveChars);
        assertEquals(0, (int) distanceToSelf);

        // Build a distance that only accepts results within a threshold of 0.
        LevenshteinDistance zeroThresholdDistance = new LevenshteinDistance(distanceToSelf);

        // The two sequences differ in length (5 vs. 3767), which exceeds the
        // threshold of 0, so the limited algorithm reports -1 ("over threshold").
        CharBuffer longSequence = CharBuffer.allocate(3767);
        Integer overThreshold = zeroThresholdDistance.apply((CharSequence) fiveChars, (CharSequence) longSequence);
        assertEquals((-1), (int) overThreshold);
    }
}
